package softuni.boardgames.web;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import softuni.boardgames.constants.ValidationBinding;
import softuni.boardgames.model.binding.UserChangeRoleBindingModel;
import softuni.boardgames.model.binding.UserRegisterBindingModel;
import softuni.boardgames.model.service.UserRoleServiceModel;
import softuni.boardgames.model.service.UserServiceModel;
import softuni.boardgames.service.UserRoleService;
import softuni.boardgames.service.UserService;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final ModelMapper modelMapper;
    private final UserRoleService userRoleService;

    public UserController(UserService userService,
                          ModelMapper modelMapper,
                          UserRoleService userRoleService) {
        this.userService = userService;
        this.modelMapper = modelMapper;
        this.userRoleService = userRoleService;
    }

    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @GetMapping("/register")
    public String register(Model model){
        if(!model.containsAttribute("userRegisterBindingModel")){
            model.addAttribute("userRegisterBindingModel", new UserRegisterBindingModel());
        }
        return "register";
    }

    @PostMapping("/register")
    public String registerConfirm(@Valid UserRegisterBindingModel userRegisterBindingModel,
                                  BindingResult bindingResult,
                                  RedirectAttributes redirectAttributes){
        if(bindingResult.hasErrors()){
            redirectAttributes.addFlashAttribute(String.format(ValidationBinding.VALIDATION,"userRegisterBindingModel"), bindingResult);
            redirectAttributes.addFlashAttribute("userRegisterBindingModel", userRegisterBindingModel);
            return "redirect:register";
        }

        if(!userRegisterBindingModel.getPassword().equals(userRegisterBindingModel.getConfirmPassword())){
            redirectAttributes.addFlashAttribute("notEquals", true);

            return "redirect:register";
        }
        UserServiceModel userServiceModel = modelMapper.map(userRegisterBindingModel, UserServiceModel.class);
        userServiceModel.setCreatedOn(LocalDateTime.now());
        userServiceModel.setLastEdited(LocalDateTime.now());
        userService.registerAndLogin(userServiceModel);

        return "redirect:/home";
    }

    @GetMapping("/change-role")
    public String changeRole(Model model){
        if(!model.containsAttribute("userChangeRoleBindingModel")){
            model.addAttribute("userChangeRoleBindingModel", new UserChangeRoleBindingModel());
        }

        List<UserServiceModel> allUsers = userService.getAllUsers();
        model.addAttribute("allUsers", allUsers);

        List<UserRoleServiceModel> userRoles = userRoleService.getAllRoles();
        model.addAttribute("userRoles", userRoles);

        return "user-change-role";
    }

    @PutMapping("/change-role")
    public String changeRoleConfirm(@Valid UserChangeRoleBindingModel userChangeRoleBindingModel,
                                    BindingResult bindingResult,
                                    RedirectAttributes redirectAttributes){

        if(bindingResult.hasErrors()){
            redirectAttributes.addFlashAttribute(String.format(ValidationBinding.VALIDATION, "userChangeRoleBindingModel"), bindingResult);
            redirectAttributes.addFlashAttribute("userChangeRoleBindingModel", userChangeRoleBindingModel);

            return "redirect:change-role";
        }

        userService.changeRoles(userChangeRoleBindingModel.getUsername(), userChangeRoleBindingModel.getUserRole());

        return "redirect:/home";
    }
}
