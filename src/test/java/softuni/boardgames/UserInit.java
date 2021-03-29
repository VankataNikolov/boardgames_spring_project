package softuni.boardgames;

import org.springframework.stereotype.Component;
import softuni.boardgames.model.entity.UserEntity;
import softuni.boardgames.model.entity.UserRoleEntity;
import softuni.boardgames.model.enums.UserRoleEnum;
import softuni.boardgames.repository.UserRepository;
import softuni.boardgames.repository.UserRoleRepository;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class UserInit {

    private UserRepository userRepository;
    private UserRoleRepository userRoleRepository;
    private String mockUserUsername;
    private String mockUserPassword;

    public UserInit(UserRepository userRepository, UserRoleRepository userRoleRepository) {
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
        this.mockUserUsername = "UserTest";
        this.mockUserPassword = "123456";
    }



    public void userEntityInit(){
        UserEntity userEntity = new UserEntity(
                this.mockUserUsername,
                this.mockUserPassword,
                List.of(
                        userRoleRepository.findByRole(UserRoleEnum.ROLE_ADMIN),
                        userRoleRepository.findByRole(UserRoleEnum.ROLE_EDITOR)
                ),
                LocalDateTime.now(),
                LocalDateTime.now()
        );
        userRepository.save(userEntity);
    }

    public void roleEntitiesInit(){
        UserRoleEntity userRoleEntityAdmin = new UserRoleEntity(UserRoleEnum.ROLE_ADMIN, "admin");
        userRoleRepository.save(userRoleEntityAdmin);

        UserRoleEntity userRoleEntityEditor = new UserRoleEntity(UserRoleEnum.ROLE_EDITOR, "editor");
        userRoleRepository.save(userRoleEntityEditor);
    }

    public void clear(){
        userRepository.deleteAll();
        userRoleRepository.deleteAll();
    }
}
