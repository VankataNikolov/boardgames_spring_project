package softuni.boardgames;

import softuni.boardgames.model.entity.UserEntity;
import softuni.boardgames.model.entity.UserRoleEntity;
import softuni.boardgames.model.enums.UserRoleEnum;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class UnitTestUserInit {

    private String mockUserUsername;
    private String mockUserPassword;

    public UnitTestUserInit() {
        this.mockUserUsername = "UserTest";
        this.mockUserPassword = "123456";
    }

    public UserEntity userEntityInit(){
        return new UserEntity(
                this.mockUserUsername,
                this.mockUserPassword,
                roleEntitiesInit(),
                LocalDateTime.now(),
                LocalDateTime.now()
        );
    }

    public UserEntity secondUserEntityInit(){
        return new UserEntity(
                "UserTest2",
                this.mockUserPassword,
                roleEntitiesInit(),
                LocalDateTime.now(),
                LocalDateTime.now()
        );
    }

    private List<UserRoleEntity> roleEntitiesInit(){
        UserRoleEntity array[] = {
                new UserRoleEntity(UserRoleEnum.ROLE_USER, "user"),
                new UserRoleEntity(UserRoleEnum.ROLE_EDITOR, "editor"),
                new UserRoleEntity(UserRoleEnum.ROLE_ADMIN, "admin")
        };

        return Stream.of(array).collect(Collectors.toCollection(ArrayList::new));
    }
}
