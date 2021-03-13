package softuni.boardgames.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import softuni.boardgames.model.binding.GameInitBindingModel;
import softuni.boardgames.model.entity.CategoryEntity;
import softuni.boardgames.model.entity.GameEntity;
import softuni.boardgames.model.entity.UserEntity;
import softuni.boardgames.model.enums.GameCategoriesEnum;
import softuni.boardgames.repository.CategoryRepository;
import softuni.boardgames.repository.GameRepository;
import softuni.boardgames.repository.UserRepository;
import softuni.boardgames.service.GameService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class GameServiceImpl implements GameService {

    private final Resource gamesFile;
    private final GameRepository gameRepository;
    private final Gson gson;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final CategoryRepository categoryRepository;

    public GameServiceImpl(@Value("classpath:init/boardgames.json") Resource gamesFile,
                           GameRepository gameRepository,
                           Gson gson,
                           UserRepository userRepository,
                           ModelMapper modelMapper,
                           CategoryRepository categoryRepository) {
        this.gamesFile = gamesFile;
        this.gameRepository = gameRepository;
        this.gson = gson;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.categoryRepository = categoryRepository;
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

    private static CategoryEntity getCategoryEntity(CategoryRepository categoryRepository, String category){
        return categoryRepository.findByName(GameCategoriesEnum.valueOf(category));
    }
}
