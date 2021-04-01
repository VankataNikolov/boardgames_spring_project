package softuni.boardgames;

import softuni.boardgames.model.entity.CategoryEntity;
import softuni.boardgames.model.entity.GameEntity;
import softuni.boardgames.model.enums.GameCategoriesEnum;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class UnitTestGameInit {

    private UnitTestUserInit unitTestUserInit = new UnitTestUserInit();

    public GameEntity gameEntityInit(){
        return new GameEntity(
                "GameTest1",
                "test1  test1 test1 test1 test1 test1",
                "img1",
                initCategories(),
                unitTestUserInit.userEntityInit()
        );
    }

    public GameEntity secondGameEntityInit(){
        return new GameEntity(
                "GameTest2",
                "test2  test2 test2 test2 test2 test2",
                "img2",
                initCategories(),
                unitTestUserInit.secondUserEntityInit()
        );
    }

    private List<CategoryEntity> initCategories(){

        CategoryEntity array[] = {
                new CategoryEntity(GameCategoriesEnum.CARDS),
                new CategoryEntity(GameCategoriesEnum.DICE)
        };

        return Stream.of(array).collect(Collectors.toCollection(ArrayList::new));
    }
}
