package softuni.boardgames.web;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import softuni.boardgames.model.view.GameAllViewModel;
import softuni.boardgames.service.GameService;

import java.util.List;

@RequestMapping("/games")
@RestController
public class GameRestController {

    private final GameService gameService;

    public GameRestController(GameService gameService) {
        this.gameService = gameService;
    }

    @GetMapping("/api")
    public ResponseEntity<List<GameAllViewModel>> getAllGames(){
        return ResponseEntity
                .ok()
                .body(gameService.serviceModelToViewAllModel(gameService.getAllGames()));
    }
}
