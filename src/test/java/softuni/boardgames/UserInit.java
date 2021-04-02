package softuni.boardgames;

import org.springframework.stereotype.Component;
import softuni.boardgames.model.entity.UserEntity;
import softuni.boardgames.model.entity.UserRoleEntity;
import softuni.boardgames.model.enums.UserRoleEnum;
import softuni.boardgames.repository.LinkRepository;
import softuni.boardgames.repository.UserRepository;
import softuni.boardgames.repository.UserRoleRepository;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class UserInit {

    private UserRepository userRepository;
    private UserRoleRepository userRoleRepository;
    private LinkRepository linkRepository;
    private String mockUserUsername;
    private String mockUserPassword;

    public UserInit(UserRepository userRepository, UserRoleRepository userRoleRepository, LinkRepository linkRepository) {
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
        this.linkRepository = linkRepository;
        this.mockUserUsername = "Test";
        this.mockUserPassword = "123456";
    }


    public void userEntitySave(UserEntity userEntity){
        userRepository.save(userEntity);
    }

    public UserEntity userEntityInit(){
        return new UserEntity(
                this.mockUserUsername,
                this.mockUserPassword,
                List.of(
                        userRoleRepository.findByRole(UserRoleEnum.ROLE_USER),
                        userRoleRepository.findByRole(UserRoleEnum.ROLE_EDITOR),
                        userRoleRepository.findByRole(UserRoleEnum.ROLE_ADMIN)
                ),
                LocalDateTime.now(),
                LocalDateTime.now()
        );
    }

    public void roleEntitiesSave(List<UserRoleEntity> roleEntities){
        roleEntities
                .forEach(re -> userRoleRepository.save(re));
    }

    public List<UserRoleEntity> roleEntitiesInit(){
        return List.of(
                new UserRoleEntity(UserRoleEnum.ROLE_USER, "user"),
                new UserRoleEntity(UserRoleEnum.ROLE_EDITOR, "editor"),
                new UserRoleEntity(UserRoleEnum.ROLE_ADMIN, "admin")
                );
    }

    public void userEntityInitAndSave(){
        roleEntitiesSave(roleEntitiesInit());
        userEntitySave(userEntityInit());
    }

    public void clear(){
        linkRepository.deleteAll();
        userRepository.deleteAll();
        userRoleRepository.deleteAll();
    }
}
