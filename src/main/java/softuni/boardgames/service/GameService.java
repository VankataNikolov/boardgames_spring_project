package softuni.boardgames.service;

import javassist.NotFoundException;
import softuni.boardgames.model.binding.GameAddBindingModel;
import softuni.boardgames.model.service.GameServiceModel;
import softuni.boardgames.model.view.GameDetailsViewModel;

import java.io.IOException;
import java.util.List;

public interface GameService {

    void seedGames();

    void addGame(GameAddBindingModel gameAddBindingModel, String username) throws IOException;

    List<GameServiceModel> getAllGames();

    GameServiceModel findGameById(Long id) throws NotFoundException;

    GameDetailsViewModel getGameDetails(Long id) throws NotFoundException;
}
