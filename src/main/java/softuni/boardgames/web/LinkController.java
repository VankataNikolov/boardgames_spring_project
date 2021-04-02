package softuni.boardgames.web;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import softuni.boardgames.constants.ValidationBinding;
import softuni.boardgames.model.service.LinkServiceModel;
import softuni.boardgames.service.LinkService;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/links")
public class LinkController {

    private final LinkService linkService;

    public LinkController(LinkService linkService) {
        this.linkService = linkService;
    }

    @GetMapping()
    public String allLinks(Model model){
        List<LinkServiceModel> allLinks = linkService.getAllLinks();
        model.addAttribute("allLinks", allLinks);

        return "links";
    }

    @GetMapping("/add")
    public String addLink(Model model){
        if(!model.containsAttribute("linkServiceModel")){
            model.addAttribute("linkServiceModel", new LinkServiceModel());
        }
        return "links-add";
    }

    @PostMapping("/add")
    public String confirmAddLink(@Valid LinkServiceModel linkServiceModel,
                                 BindingResult bindingResult,
                                 RedirectAttributes redirectAttributes,
                                 @AuthenticationPrincipal UserDetails principal){
        if(bindingResult.hasErrors()){
            redirectAttributes.addFlashAttribute(String.format(ValidationBinding.VALIDATION, "linkServiceModel"), bindingResult);
            redirectAttributes.addFlashAttribute("linkServiceModel", linkServiceModel);
            return "redirect:add";
        }
        linkServiceModel.setAddedBy(principal.getUsername());
        linkService.addLink(linkServiceModel);

        return "redirect:/links";
    }
}
