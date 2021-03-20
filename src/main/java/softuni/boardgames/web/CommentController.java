package softuni.boardgames.web;

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
import java.util.List;

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
    public String gameComments(@PathVariable Long id,
                               @RequestParam(value = "name") String name,
                               Model model,
                               @AuthenticationPrincipal UserDetails principal){

        List<CommentServiceModel> gameComments = commentService.getGameComments(id);

        model.addAttribute("comments", gameComments);
        model.addAttribute("name", name);
        model.addAttribute("principalUsername",principal.getUsername() );

        return "game-comments";
    }

    @GetMapping("/{id}/add")
    public String add(@PathVariable Long id, Model model){
        if(!model.containsAttribute("commentAddBindingModel")){
            model.addAttribute("commentAddBindingModel", new CommentAddBindingModel());
        }
        model.addAttribute("gameId", id);

        return "comments-add";
    }

    @PostMapping("{id}/add")
    public String add(@PathVariable Long id, @Valid CommentAddBindingModel commentAddBindingModel,
                      BindingResult bindingResult,
                      RedirectAttributes redirectAttributes,
                      @AuthenticationPrincipal UserDetails principal) {

        if(bindingResult.hasErrors()){
            redirectAttributes.addFlashAttribute(String.format(ValidationBinding.VALIDATION, "commentAddBindingModel"), bindingResult);
            redirectAttributes.addFlashAttribute("commentAddBindingModel", commentAddBindingModel);

            return "redirect:/comments/" + id + "/add";
        }

        CommentServiceModel commentServiceModel = modelMapper.map(commentAddBindingModel, CommentServiceModel.class);
        commentServiceModel.setAuthorName(principal.getUsername());
        commentServiceModel.setGameId(id);
        commentServiceModel.setCreatedOn(LocalDateTime.now());
        commentServiceModel.setLastEdited(LocalDateTime.now());

        commentService.addComment(commentServiceModel);

        return "redirect:/games/" + id + "/details";
    }

    @DeleteMapping("/{id}/delete")
    public String deleteComment(@PathVariable Long id,
                                @RequestParam(value = "gameId") Long gameId){
        commentService.deleteComment(id);

        return "redirect:/games/" + gameId + "/details";
    }
}
