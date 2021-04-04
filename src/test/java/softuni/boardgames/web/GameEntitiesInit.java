package softuni.boardgames.web;

import org.springframework.stereotype.Component;
import softuni.boardgames.model.entity.GameEntity;
import softuni.boardgames.model.enums.GameCategoriesEnum;
import softuni.boardgames.repository.CategoryRepository;
import softuni.boardgames.repository.GameRepository;
import softuni.boardgames.repository.UserRepository;
import softuni.boardgames.service.CategoryService;

import java.util.List;

@Component
public class GameEntitiesInit {

    private UserRepository userRepository;
    private CategoryService categoryService;
    private CategoryRepository categoryRepository;
    private GameRepository gameRepository;

    public GameEntitiesInit(UserRepository userRepository,
                            CategoryService categoryService,
                            CategoryRepository categoryRepository,
                            GameRepository gameRepository) {
        this.userRepository = userRepository;
        this.categoryService = categoryService;
        this.categoryRepository = categoryRepository;
        this.gameRepository = gameRepository;
    }

    public void categoryEntitiesInit(){
        categoryService.initCategories();
    }

    public void gameEntitiesInit(){
        GameEntity gameEntityTest1 = new GameEntity(
                "GameTest1",
                "test test test test test test test test test test test",
                "test.jpg",
                categoryRepository.findAll(),
                userRepository.findByUsername("Test").get()
        );

        GameEntity gameEntityTest2 = new GameEntity(
                "GameTest2",
                "test test test test test test test test test test test",
                "test.jpg",
                List.of(categoryRepository.findByName(GameCategoriesEnum.CARDS)),
                userRepository.findByUsername("Test").get()
        );

        gameRepository.save(gameEntityTest1);
    }

    public void init(){
        categoryEntitiesInit();
        gameEntitiesInit();
    }

    public void clear(){
        gameRepository.deleteAll();
        categoryRepository.deleteAll();
    }
}
