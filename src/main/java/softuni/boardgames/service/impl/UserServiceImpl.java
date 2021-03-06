package softuni.boardgames.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import softuni.boardgames.model.entity.UserEntity;
import softuni.boardgames.model.entity.UserRoleEntity;
import softuni.boardgames.model.enums.UserRoleEnum;
import softuni.boardgames.model.service.UserServiceModel;
import softuni.boardgames.repository.UserRepository;
import softuni.boardgames.repository.UserRoleRepository;
import softuni.boardgames.service.UserService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final UserRoleRepository userRoleRepository;

    public UserServiceImpl(UserRepository userRepository,
                           ModelMapper modelMapper,
                           PasswordEncoder passwordEncoder,
                           UserRoleRepository userRoleRepository) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
        this.userRoleRepository = userRoleRepository;
    }

    @Override
    public void seedUsers() {
        if (userRepository.count() == 0) {
            List<UserEntity> userEntities = List.of(new UserEntity(
                            "admin",
                            passwordEncoder.encode("123456"),
                            setUserRolesList(userRoleRepository, UserRoleEnum.ROLE_ADMIN),
                            LocalDateTime.now()
                    ),
                    new UserEntity(
                            "editor",
                            passwordEncoder.encode("123456"),
                            setUserRolesList(userRoleRepository, UserRoleEnum.ROLE_EDITOR),
                            LocalDateTime.now()
                    ),
                    new UserEntity(
                            "user",
                            passwordEncoder.encode("123456"),
                            setUserRolesList(userRoleRepository, UserRoleEnum.ROLE_USER),
                            LocalDateTime.now()
                    )
            );
            userRepository.saveAll(userEntities);
        }

    }

    @Override
    public UserServiceModel findUserByUsername(String username) {
        Optional<UserEntity> byUsername = userRepository.findByUsername(username);
        if(byUsername.isEmpty()){
            return null;
        }
        return modelMapper.map(byUsername, UserServiceModel.class);
    }

    private static List<UserRoleEntity> setUserRolesList(UserRoleRepository userRoleRepository, UserRoleEnum userRoleEnum) {
        List<UserRoleEntity> userRoleEntities = new ArrayList<>();
        if (userRoleEnum == UserRoleEnum.ROLE_USER) {
            userRoleEntities.add(userRoleRepository.findByRole(UserRoleEnum.ROLE_USER));
        } else if (userRoleEnum == UserRoleEnum.ROLE_EDITOR) {
            userRoleEntities = List.of(userRoleRepository.findByRole(UserRoleEnum.ROLE_USER),
                    userRoleRepository.findByRole(UserRoleEnum.ROLE_EDITOR));
        } else {
            userRoleEntities = userRoleRepository.findAll();
        }
        return userRoleEntities;
    }
}
