package softuni.boardgames.web;

import javassist.NotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import softuni.boardgames.constants.ValidationBinding;
import softuni.boardgames.model.binding.GameAddBindingModel;
import softuni.boardgames.model.enums.GameCategoriesEnum;
import softuni.boardgames.model.view.GameAllViewModel;
import softuni.boardgames.model.view.GameDetailsViewModel;
import softuni.boardgames.service.GameService;

import javax.validation.Valid;
import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/games")
public class GameController {

    private final GameService gameService;
    private final ModelMapper modelMapper;

    public GameController(GameService gameService,
                          ModelMapper modelMapper) {
        this.gameService = gameService;
        this.modelMapper = modelMapper;
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

        List<GameCategoriesEnum> categories = Arrays.asList(GameCategoriesEnum.values());
        model.addAttribute("allCategories", categories);

        return "games-add";
    }

    @PostMapping("/add")
    public String addGameConfirm(@Valid GameAddBindingModel gameAddBindingModel,
                                 BindingResult bindingResult,
                                 RedirectAttributes redirectAttributes,
                                 Principal principal) throws IOException {
        if(bindingResult.hasErrors()){
            redirectAttributes.addFlashAttribute(String.format(ValidationBinding.VALIDATION, "gameAddBindingModel"), bindingResult);
            redirectAttributes.addFlashAttribute("gameAddBindingModel", gameAddBindingModel);

            return "redirect:add";
        }

        gameService.addGame(gameAddBindingModel, principal.getName());

        return "redirect:all";
    }

    @GetMapping("/{id}/details")
    public String gameDetails(@PathVariable Long id, Model model) throws NotFoundException {
        GameDetailsViewModel gameDetails = gameService.getGameDetails(id);

        model.addAttribute("gameDetails", gameDetails);

        return "games-details";
    }

}
