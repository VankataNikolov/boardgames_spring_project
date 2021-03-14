package softuni.boardgames.service.impl;

import com.google.gson.Gson;
import javassist.NotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import softuni.boardgames.model.binding.GameAddBindingModel;
import softuni.boardgames.model.binding.GameInitBindingModel;
import softuni.boardgames.model.entity.CategoryEntity;
import softuni.boardgames.model.entity.GameEntity;
import softuni.boardgames.model.entity.GameImagesEntity;
import softuni.boardgames.model.entity.UserEntity;
import softuni.boardgames.model.enums.GameCategoriesEnum;
import softuni.boardgames.model.service.GameServiceModel;
import softuni.boardgames.model.service.UserServiceModel;
import softuni.boardgames.model.view.GameDetailsViewModel;
import softuni.boardgames.repository.CategoryRepository;
import softuni.boardgames.repository.GameRepository;
import softuni.boardgames.repository.UserRepository;
import softuni.boardgames.service.CloudinaryService;
import softuni.boardgames.service.GameImageService;
import softuni.boardgames.service.GameService;
import softuni.boardgames.service.UserService;

import javax.persistence.NoResultException;
import javax.validation.constraints.Size;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GameServiceImpl implements GameService {

    private final Resource gamesFile;
    private final GameRepository gameRepository;
    private final Gson gson;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final CategoryRepository categoryRepository;
    private final CloudinaryService cloudinaryService;
    private final GameImageService gameImageService;

    public GameServiceImpl(@Value("classpath:init/boardgames.json") Resource gamesFile,
                           GameRepository gameRepository,
                           Gson gson,
                           UserRepository userRepository,
                           ModelMapper modelMapper,
                           CategoryRepository categoryRepository,
                           CloudinaryService cloudinaryService,
                           GameImageService gameImageService) {
        this.gamesFile = gamesFile;
        this.gameRepository = gameRepository;
        this.gson = gson;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.categoryRepository = categoryRepository;
        this.cloudinaryService = cloudinaryService;
        this.gameImageService = gameImageService;
    }

    @Override
    public void seedGames() {
        if (gameRepository.count() == 0) {
            UserEntity addedByUser = userRepository.findByUsername("admin").orElse(null);
            try {
                String boardgamesFile = Files.readString(Path.of(gamesFile.getURI()));
                GameInitBindingModel[] gameInitBindingModels =
                        gson.fromJson(boardgamesFile, GameInitBindingModel[].class);
                Arrays.stream(gameInitBindingModels).
                        forEach(gi -> {
                            GameEntity newGameEntity = new GameEntity(gi.getName(), gi.getDescription(), addedByUser);
                            newGameEntity.setCreatedOn(LocalDateTime.now());
                            newGameEntity.setLastEdited(LocalDateTime.now());
                            List<CategoryEntity> categoryEntities = new ArrayList<>();
                            Arrays.stream(gi.getCategories())
                                    .forEach(c ->
                                        categoryEntities.add(getCategoryEntity(categoryRepository, c)));
                            newGameEntity.setCategories(categoryEntities);
                            gameRepository.save(newGameEntity);
                        });
            } catch (IOException e) {
                throw new IllegalStateException("Cannot seed games... :(");
            }
        }
    }

    @Override
    public void addGame(GameAddBindingModel gameAddBindingModel, String username) throws IOException {
        String name = gameAddBindingModel.getName();
        String description = gameAddBindingModel.getDescription();
        String titleImgUrl = cloudinaryService.uploadImage(gameAddBindingModel.getTitleImgUrl());

        UserEntity addedByUserEntity = userRepository.findByUsername(username).get();

        List<CategoryEntity> categoryEntities = gameAddBindingModel.getCategories()
                .stream()
                .map(c -> getCategoryEntity(categoryRepository, c))
                .collect(Collectors.toList());

        GameEntity newGame = new GameEntity(name, description, titleImgUrl, categoryEntities, addedByUserEntity);
        newGame.setCreatedOn(LocalDateTime.now());
        newGame.setLastEdited(LocalDateTime.now());

        GameEntity savedGame = gameRepository.save(newGame);

        gameImageService.addPictures(gameAddBindingModel.getPictures(), savedGame);
    }

    @Override
    public List<GameServiceModel> getAllGames() {
        return gameRepository.findAll(Sort.by(Sort.Direction.ASC, "createdOn"))
                .stream()
                .map(ge -> modelMapper.map(ge, GameServiceModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public GameServiceModel findGameById(Long id) {
        return modelMapper.map(gameRepository.findById(id), GameServiceModel.class);
    }

    @Override
    public GameDetailsViewModel getGameDetails(Long id) {
        GameServiceModel gameByIdService = findGameById(id);
        GameDetailsViewModel gameByIdView = modelMapper.map(gameByIdService, GameDetailsViewModel.class);

        List<@Size(max = 255) String> pictureUrls = gameByIdService.getPictures()
                .stream()
                .map(GameImagesEntity::getUrl)
                .collect(Collectors.toList());

        List<String> categoryNames = gameByIdService.getCategories()
                .stream()
                .map(c -> c.getName().name())
                .collect(Collectors.toList());

        gameByIdView.setImgUrls(pictureUrls);
        gameByIdView.setCategories(categoryNames);

        return gameByIdView;
    }

    private static CategoryEntity getCategoryEntity(CategoryRepository categoryRepository, String category){
        return categoryRepository.findByName(GameCategoriesEnum.valueOf(category));
    }
}
