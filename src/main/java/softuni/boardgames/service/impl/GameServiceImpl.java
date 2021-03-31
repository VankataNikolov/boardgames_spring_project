package softuni.boardgames.service.impl;

import javassist.NotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import softuni.boardgames.model.binding.GameAddBindingModel;
import softuni.boardgames.model.binding.GameEditBindingModel;
import softuni.boardgames.model.entity.CategoryEntity;
import softuni.boardgames.model.entity.GameEntity;
import softuni.boardgames.model.entity.GameImagesEntity;
import softuni.boardgames.model.entity.UserEntity;
import softuni.boardgames.model.enums.GameCategoriesEnum;
import softuni.boardgames.model.service.GameServiceModel;
import softuni.boardgames.model.service.UserServiceModel;
import softuni.boardgames.model.view.GameAllViewModel;
import softuni.boardgames.model.view.GameDetailsViewModel;
import softuni.boardgames.repository.CategoryRepository;
import softuni.boardgames.repository.GameRepository;
import softuni.boardgames.repository.UserRepository;
import softuni.boardgames.service.CloudinaryService;
import softuni.boardgames.service.GameImageService;
import softuni.boardgames.service.GameService;

import javax.validation.constraints.Size;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GameServiceImpl implements GameService {

    private final GameRepository gameRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final CategoryRepository categoryRepository;
    private final CloudinaryService cloudinaryService;
    private final GameImageService gameImageService;

    public GameServiceImpl(GameRepository gameRepository,
                           UserRepository userRepository,
                           ModelMapper modelMapper,
                           CategoryRepository categoryRepository,
                           CloudinaryService cloudinaryService,
                           GameImageService gameImageService) {
        this.gameRepository = gameRepository;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.categoryRepository = categoryRepository;
        this.cloudinaryService = cloudinaryService;
        this.gameImageService = gameImageService;
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

    @Cacheable("allGames")
    @Override
    public List<GameServiceModel> getAllGames() {
        return entityToServiceModel(gameRepository.findAll(Sort.by(Sort.Direction.ASC, "name")));

    }

    @CacheEvict(cacheNames = "allGames", allEntries = true)
    @Override
    public void evictCacheAllGames() {}

    @Override
    public void editGame(GameEditBindingModel gameEditBindingModel) throws NotFoundException {
        Long gameId = gameEditBindingModel.getId();
        GameEntity gameEntity = gameRepository.findById(gameId)
                .orElseThrow(() -> new NotFoundException("game with id "+ gameId +" not found"));

        gameEntity.setDescription(gameEditBindingModel.getDescription());
        gameEntity.setName(gameEditBindingModel.getName());
        gameEntity.getCategories().clear();
        gameEditBindingModel.getCategories()
                .forEach(c -> gameEntity.getCategories().add(getCategoryEntity(categoryRepository, c)));
        gameEntity.setLastEdited(LocalDateTime.now());
        gameRepository.save(gameEntity);

    }

    @Override
    public GameServiceModel findGameById(Long id) throws NotFoundException {
        GameEntity gameEntity = gameRepository.findById(id).orElseThrow(() -> new NotFoundException("game with id "+ id +" not found"));
        GameServiceModel mappedGame = modelMapper.map(gameEntity, GameServiceModel.class);
        UserEntity userEntity = userRepository.findByUsername(gameEntity.getAddedBy().getUsername()).
                orElseThrow(() -> new NotFoundException("user not found"));
        mappedGame.setAddedBy(modelMapper.map(userEntity, UserServiceModel.class));

        return mappedGame;
    }

    @Override
    public GameDetailsViewModel getGameDetails(Long id) throws NotFoundException {
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

    @Override
    public List<GameAllViewModel> serviceModelToViewAllModel(List<GameServiceModel> gameServiceModels) {
        return gameServiceModels
                .stream()
                .map(gm -> {
                    GameAllViewModel mappedGame = modelMapper.map(gm, GameAllViewModel.class);
                    List<GameCategoriesEnum> categoriesEnums = gm.getCategories()
                            .stream()
                            .map(CategoryEntity::getName)
                            .collect(Collectors.toList());
                    mappedGame.setCategories(categoriesEnums);
                    return mappedGame;
                })
                .collect(Collectors.toList());
    }

    @Override
    public GameEditBindingModel serviceModelToEditBindingModel(GameServiceModel gameServiceModel) {

        GameEditBindingModel gameEditBindingModel = modelMapper.map(gameServiceModel, GameEditBindingModel.class);
        List<String> categoryNames = gameServiceModel.getCategories()
                .stream()
                .map(categoryEntity -> categoryEntity.getName().name())
                .collect(Collectors.toList());
        gameEditBindingModel.getCategories().clear();
        categoryNames
                .forEach(cn -> gameEditBindingModel.getCategories().add(cn));
        return gameEditBindingModel;

    }


    @Override
    public List<GameServiceModel> findGamesByCategory(String category) {
        return entityToServiceModel(gameRepository.findAllByCategoriesContains(getCategoryEntity(categoryRepository, category)));
    }

    @Override
    public List<GameServiceModel> entityToServiceModel(List<GameEntity> gameEntities) {
        return gameEntities
                .stream()
                .map(ge -> modelMapper.map(ge, GameServiceModel.class))
                .collect(Collectors.toList());
    }

    private static CategoryEntity getCategoryEntity(CategoryRepository categoryRepository, String category){
        return categoryRepository.findByName(GameCategoriesEnum.valueOf(category));
    }
}
