package softuni.boardgames.web;

import javassist.NotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import softuni.boardgames.constants.ValidationBinding;
import softuni.boardgames.model.binding.GameAddBindingModel;
import softuni.boardgames.model.entity.CategoryEntity;
import softuni.boardgames.model.enums.GameCategoriesEnum;
import softuni.boardgames.model.view.GameAllViewModel;
import softuni.boardgames.model.view.GameDetailsViewModel;
import softuni.boardgames.service.GameService;

import javax.validation.Valid;
import java.io.IOException;
import java.security.Principal;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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
    public String allGames(Model model){

        List<GameAllViewModel> allGames = gameService.getAllGames()
                .stream()
                .map(gm -> {
                    GameAllViewModel mappedGame = modelMapper.map(gm, GameAllViewModel.class);
                    List<GameCategoriesEnum> categoriesEnums = gm.getCategories()
                            .stream()
                            .map(CategoryEntity::getName)
                            .collect(Collectors.toList());
                    mappedGame.setCategories(categoriesEnums);
                    return mappedGame;
                })
                .collect(Collectors.toList());

        allGames.get(0).getCategories()
                .forEach(System.out::println);
        model.addAttribute("allGames", allGames);

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
