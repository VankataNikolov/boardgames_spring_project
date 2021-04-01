package softuni.boardgames.service.impl;

import javassist.NotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import softuni.boardgames.UnitTestGameInit;
import softuni.boardgames.UnitTestUserInit;
import softuni.boardgames.model.binding.GameEditBindingModel;
import softuni.boardgames.model.entity.CategoryEntity;
import softuni.boardgames.model.entity.GameEntity;
import softuni.boardgames.model.entity.UserEntity;
import softuni.boardgames.model.enums.GameCategoriesEnum;
import softuni.boardgames.model.service.GameServiceModel;
import softuni.boardgames.repository.CategoryRepository;
import softuni.boardgames.repository.GameRepository;
import softuni.boardgames.repository.UserRepository;
import softuni.boardgames.service.GameImageService;
import softuni.boardgames.service.GameService;

import java.util.List;

import static org.mockito.ArgumentMatchers.*;

@ExtendWith(MockitoExtension.class)
public class GameServiceImplTest {

    private UnitTestGameInit unitTestGameInit;
    private UnitTestUserInit unitTestUserInit;
    private GameService gameServiceTest;
    private GameEntity gameEntity1, gameEntity2;
    private UserEntity testUserEntity;
    private ModelMapper modelMapper;

    @Mock
    GameRepository mockedGameRepository;
    @Mock
    private UserRepository mockedUserRepository;
    @Mock
    private CategoryRepository mockedCategoryRepository;
    @Mock
    private CloudinaryServiceImpl mockedCloudinaryService;
    @Mock
    private GameImageService mockedGameImageService;

    public GameServiceImplTest() {
        this.unitTestGameInit = new UnitTestGameInit();
        this.modelMapper = new ModelMapper();
        this.unitTestUserInit = new UnitTestUserInit();
    }

    @BeforeEach
    void setUp(){
        this.gameEntity1 = unitTestGameInit.gameEntityInit();
        this.gameEntity2 = unitTestGameInit.secondGameEntityInit();

        this.testUserEntity = unitTestUserInit.mockedUserEntityInit();

        this.gameServiceTest = new GameServiceImpl(
                mockedGameRepository,
                mockedUserRepository,
                modelMapper,
                mockedCategoryRepository,
                mockedCloudinaryService,
                mockedGameImageService
        );
    }

    @Test
    void shouldReturnCorrectBindingModel(){
        GameEditBindingModel actual = gameServiceTest
                .serviceModelToEditBindingModel(modelMapper.map(gameEntity1, GameServiceModel.class));
        Assertions.assertEquals(gameEntity1.getName(), actual.getName());
        Assertions.assertEquals(gameEntity1.getCategories().size(), actual.getCategories().size());
        Assertions.assertEquals(gameEntity1.getCategories().get(0).getName().name(), actual.getCategories().get(0));
        Assertions.assertEquals(gameEntity1.getCategories().get(1).getName().name(), actual.getCategories().get(1));
        Assertions.assertEquals(gameEntity1.getDescription(), actual.getDescription());
    }

    @Test
    void gameEditTest(){
        GameEditBindingModel gameEditBindingModel = new GameEditBindingModel();
        gameEditBindingModel.setId(1L);
        gameEditBindingModel.setName(gameEntity1.getName());
        gameEditBindingModel.setDescription("one one one one one one one");
        gameEditBindingModel.setCategories(List.of("CARDS"));
        Mockito.when(this.mockedGameRepository.findById(1L))
                .thenReturn(java.util.Optional.ofNullable(gameEntity1));
        Mockito.when(this.mockedCategoryRepository.findByName(GameCategoriesEnum.CARDS))
                .thenReturn(new CategoryEntity(GameCategoriesEnum.CARDS));
        Mockito.when(this.mockedGameRepository.save(any(GameEntity.class)))
                .thenReturn(gameEntity1);
        try {
            gameServiceTest.editGame(gameEditBindingModel);
        } catch (NotFoundException e) {
            e.printStackTrace();
        }
        Mockito.verify(mockedGameRepository, Mockito.times(1)).save(gameEntity1);
        Assertions.assertEquals(1, gameEntity1.getCategories().size());
    }
}
