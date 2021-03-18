package softuni.boardgames.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/comments")
public class CommentController {

    @GetMapping("/add")
    public String add(@ModelAttribute("gameId") Long gameId){

        //ToDo

        return "comments-add";
    }

    @PostMapping("/add")
    public String add(){

        //ToDo

        return "comments-add";
    }
}
