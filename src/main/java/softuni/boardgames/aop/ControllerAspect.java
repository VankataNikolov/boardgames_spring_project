package softuni.boardgames.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Aspect
@Component
public class ControllerAspect {

    @Pointcut("execution(* softuni.boardgames.web.GameController.addGameConfirm(..))")
    public void addGamePointcut(){}

    @Pointcut("execution(* softuni.boardgames.web.GameController.allGames(..))")
    public void allGamesPointcut() {}

    @AfterReturning(value = "addGamePointcut()", returning="returningView")
    public void afterAdviceAddGame(JoinPoint joinPoint, String returningView){

        if(returningView.equals("redirect:all")){
            Object[] args = joinPoint.getArgs();
            for(Object arg : args){
                System.out.println(arg.getClass().getName());
                if(isRedirectAttributesClass(arg)){
                    RedirectAttributes redirectAttributes = (RedirectAttributes) arg;
                    redirectAttributes.addFlashAttribute("isOk", true);
                    break;
                }
            }
        }
    }



    @After("allGamesPointcut()")
    public void afterAdviceAllGames(JoinPoint joinPoint){

        Object[] args = joinPoint.getArgs();
        for(Object arg : args){
            if(isModelClass(arg)){
                Model model = (Model) arg;
                if(!model.containsAttribute("isOk")){
                    model.addAttribute("isOk", false);
                }
                break;
            }
        }
    }

    private boolean isRedirectAttributesClass(Object o) {
        return o.getClass().getName().equals("org.springframework.web.servlet.mvc.support.RedirectAttributesModelMap");
    }

    private boolean isModelClass(Object o){
        return o.getClass().getName().equals("org.springframework.validation.support.BindingAwareModelMap");
    }
}
