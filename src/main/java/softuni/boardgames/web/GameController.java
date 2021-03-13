package softuni.boardgames.web;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import softuni.boardgames.constants.ValidationBinding;
import softuni.boardgames.model.binding.GameAddBindingModel;
import softuni.boardgames.model.enums.GameCategoriesEnum;
import softuni.boardgames.service.CategoryService;
import softuni.boardgames.service.GameService;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/games")
public class GameController {

    private final GameService gameService;
    private final CategoryService categoryService;
    private final ModelMapper modelMapper;

    public GameController(GameService gameService,
                          CategoryService categoryService,
                          ModelMapper modelMapper) {
        this.gameService = gameService;
        this.categoryService = categoryService;
        this.modelMapper = modelMapper;
    }


    @GetMapping("/all")
    public String allGames(Model model){



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
                                 RedirectAttributes redirectAttributes){
        if(bindingResult.hasErrors()){
            redirectAttributes.addFlashAttribute(String.format(ValidationBinding.VALIDATION, "gameAddBindingModel"), bindingResult);
            redirectAttributes.addFlashAttribute("gameAddBindingModel", gameAddBindingModel);

            return "redirect:add";
        }

        //ToDo save new game in DB

        return "games-all";
    }
}
