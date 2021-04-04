package softuni.boardgames.service;

import javassist.NotFoundException;
import softuni.boardgames.model.binding.GameAddBindingModel;
import softuni.boardgames.model.binding.GameEditBindingModel;
import softuni.boardgames.model.entity.GameEntity;
import softuni.boardgames.model.service.GameServiceModel;
import softuni.boardgames.model.view.GameAllViewModel;
import softuni.boardgames.model.view.GameDetailsViewModel;

import java.io.IOException;
import java.util.List;

public interface GameService {

    void addGame(GameAddBindingModel gameAddBindingModel, String username) throws IOException;

    List<GameServiceModel> getAllGames();

    GameServiceModel findGameById(Long id);

    GameDetailsViewModel getGameDetails(Long id);

    List<GameAllViewModel> serviceModelToViewAllModel(List<GameServiceModel> gameServiceModels);

    List<GameServiceModel> findGamesByCategory(String category);

    List<GameServiceModel> entityToServiceModel(List<GameEntity> gameEntities);

    GameEditBindingModel serviceModelToEditBindingModel(GameServiceModel gameServiceModel);

    void evictCacheAllGames();

    void editGame(GameEditBindingModel gameEditBindingModel);
}
