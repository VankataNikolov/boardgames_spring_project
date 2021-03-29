package softuni.boardgames.web;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

@Controller
public class AppErrorController implements ErrorController {

    @RequestMapping("/error")
    public String errorHandle(HttpServletRequest request){
        Object statusCode = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        if(statusCode != null){
            int statusCodeNumber = Integer.parseInt(statusCode.toString());

            if(statusCodeNumber == HttpStatus.NOT_FOUND.value()){
                return "error-404";
            }
        }

        return "error-app";
    }

    @Override
    public String getErrorPath() {
        return "/error";
    }
}
