package softuni.boardgames.web;

import javassist.NotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import softuni.boardgames.constants.ValidationBinding;
import softuni.boardgames.model.binding.CommentAddBindingModel;
import softuni.boardgames.model.service.CommentServiceModel;
import softuni.boardgames.service.CommentService;

import javax.validation.Valid;
import java.time.LocalDateTime;

@Controller
@RequestMapping("/comments")
public class CommentController {

    private final CommentService commentService;
    private final ModelMapper modelMapper;

    public CommentController(CommentService commentService,
                             ModelMapper modelMapper) {
        this.commentService = commentService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/{id}/all")
    public String gameComments(@PathVariable Long id, @RequestParam(value = "name") String name, Model model){

        model.addAttribute("comments", commentService.getGameComments(id));
        model.addAttribute("name", name);

        return "game-comments";
    }

    @GetMapping("/{id}/add")
    public String add(@PathVariable Long id, Model model){
        if(!model.containsAttribute("commentBindingModel")){
            model.addAttribute("commentAddBindingModel", new CommentAddBindingModel());
        }
        model.addAttribute("gameId", id);

        return "comments-add";
    }

    @PostMapping("{id}/add")
    public String add(@PathVariable Long id, @Valid CommentAddBindingModel commentAddBindingModel,
                      BindingResult bindingResult,
                      RedirectAttributes redirectAttributes,
                      @AuthenticationPrincipal UserDetails principal) throws NotFoundException {

        if(bindingResult.hasErrors()){
            redirectAttributes.addFlashAttribute(String.format(ValidationBinding.VALIDATION, "commentAddBindingModel"), bindingResult);
            redirectAttributes.addFlashAttribute("commentAddBindingModel", commentAddBindingModel);

            return "redirect:" + id + "add";
        }

        CommentServiceModel commentServiceModel = modelMapper.map(commentAddBindingModel, CommentServiceModel.class);
        commentServiceModel.setAuthor(principal.getUsername());
        commentServiceModel.setGame(id);
        commentServiceModel.setCreatedOn(LocalDateTime.now());
        commentServiceModel.setLastEdited(LocalDateTime.now());

        commentService.addComment(commentServiceModel);

        return "redirect:/games/" + id + "/details";
    }
}
