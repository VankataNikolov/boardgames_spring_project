package softuni.boardgames.web;


import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import softuni.boardgames.eventcustom.GameStatisticEventPublisher;
import softuni.boardgames.constants.ValidationBinding;
import softuni.boardgames.model.binding.GameAddBindingModel;
import softuni.boardgames.model.binding.GameEditBindingModel;
import softuni.boardgames.model.enums.GameCategoriesEnum;
import softuni.boardgames.model.service.GameServiceModel;
import softuni.boardgames.model.service.GameStatisticServiceModel;
import softuni.boardgames.model.view.GameAllViewModel;
import softuni.boardgames.model.view.GameDetailsViewModel;
import softuni.boardgames.service.GameService;
import softuni.boardgames.service.GameStatisticService;

import javax.validation.Valid;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/games")
public class GameController {

    private final GameService gameService;
    private final GameStatisticEventPublisher gameStatisticEventPublisher;
    private final GameStatisticService gameStatisticService;

    public GameController(GameService gameService,
                          GameStatisticEventPublisher gameStatisticEventPublisher,
                          GameStatisticService gameStatisticService) {
        this.gameService = gameService;
        this.gameStatisticEventPublisher = gameStatisticEventPublisher;
        this.gameStatisticService = gameStatisticService;
    }


    @GetMapping("/all")
    public String allGames(@RequestParam(defaultValue = "ALL") String category, Model model){
        List<GameAllViewModel> allGames;

        if(category.equals("ALL")) {
            allGames = gameService.serviceModelToViewAllModel(gameService.getAllGames());

        } else {
            allGames = gameService.serviceModelToViewAllModel(gameService.findGamesByCategory(category));
        }

        if (allGames.size() == 0) {
            return "index";
        }

        model.addAttribute("allGames", allGames);


        GameCategoriesEnum[] categoriesEnums = GameCategoriesEnum.values();
        List<String> allCategories = new ArrayList<>();
        allCategories.add("ALL");
        for (GameCategoriesEnum categoriesEnum : categoriesEnums) {
            allCategories.add(categoriesEnum.name());
        }

        model.addAttribute("allCategories", allCategories);

        return "games-all";
    }

    @GetMapping("/add")
    public String addGame(Model model){
        if(!model.containsAttribute("gameAddBindingModel")){
            model.addAttribute("gameAddBindingModel", new GameAddBindingModel());
        }

        model.addAttribute("allCategories", this.getCategories());

        return "games-add";
    }

    @PostMapping("/add")
    public String addGameConfirm(@Valid GameAddBindingModel gameAddBindingModel,
                                 BindingResult bindingResult,
                                 RedirectAttributes redirectAttributes,
                                 @AuthenticationPrincipal UserDetails principal) throws IOException {
        if(bindingResult.hasErrors()){
            redirectAttributes.addFlashAttribute(String.format(ValidationBinding.VALIDATION, "gameAddBindingModel"), bindingResult);
            redirectAttributes.addFlashAttribute("gameAddBindingModel", gameAddBindingModel);

            return "redirect:add";
        }

        gameService.addGame(gameAddBindingModel, principal.getUsername());

        return "redirect:all";
    }

    @GetMapping("/{id}/details")
    public String gameDetails(@PathVariable Long id, Model model) {
        GameDetailsViewModel gameDetails = gameService.getGameDetails(id);
        gameStatisticEventPublisher.publishEvent(gameDetails.getName());
        model.addAttribute("gameDetails", gameDetails);

        return "games-details";
    }

    @GetMapping("/edit/select")
    public String editSelect(Model model){
        model.addAttribute("allGames", gameService.getAllGames());
        return "games-edit-select";
    }

    @GetMapping("/edit")
    public String edit(@RequestParam Long gameId){
        return "redirect:/games/" + gameId + "/edit";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model){
        if(!model.containsAttribute("editError")){
            model.addAttribute("editError", false);
        }

        if(!((boolean) model.getAttribute("editError"))){
            GameServiceModel gameServiceById;

            gameServiceById = gameService.findGameById(id);
            GameEditBindingModel gameEditBindingModel = gameService.serviceModelToEditBindingModel(gameServiceById);
            model.addAttribute("gameEditBindingModel", gameEditBindingModel);
        }
        model.addAttribute("allCategories", this.getCategories());
        return "games-edit";
    }

    @PostMapping("/{id}/edit")
    public String editFormConfirm(@Valid GameEditBindingModel gameEditBindingModel,
                                  BindingResult bindingResult,
                                  RedirectAttributes redirectAttributes, @PathVariable Long id){

        if(bindingResult.hasErrors()){
            redirectAttributes.addFlashAttribute(String.format(ValidationBinding.VALIDATION, "gameEditBindingModel"), bindingResult);
            redirectAttributes.addFlashAttribute("gameEditBindingModel", gameEditBindingModel);
            redirectAttributes.addFlashAttribute("editError", true);
            return "redirect:/games/" + id + "/edit";
        }

        gameEditBindingModel.setId(id);
        gameService.editGame(gameEditBindingModel);
        gameService.evictCacheAllGames();
        return "redirect:/games/all";
    }

    @GetMapping("/stats/visits")
    public String gamesVisits(Model model){
        List<GameStatisticServiceModel> gameVisits = gameStatisticService.getAll();
        model.addAttribute("gameVisitsList", gameVisits);

        int sumVisits = gameVisits.stream()
                .mapToInt(GameStatisticServiceModel::getVisits)
                .sum();

        model.addAttribute("sumVisits", sumVisits);
        return "games-stats-visits";
    }


    private List<GameCategoriesEnum> getCategories(){
        return Arrays.asList(GameCategoriesEnum.values());
    }
}
