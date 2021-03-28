package softuni.boardgames.service.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import softuni.boardgames.model.entity.UserEntity;
import softuni.boardgames.model.entity.UserRoleEntity;
import softuni.boardgames.model.enums.UserRoleEnum;
import softuni.boardgames.model.service.UserServiceModel;
import softuni.boardgames.repository.UserRepository;
import softuni.boardgames.repository.UserRoleRepository;

import java.time.LocalDateTime;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {

    private UserServiceImpl userServiceTest;
    private UserEntity userEntityTest;
    private UserServiceModel userTest;
    private ModelMapper modelMapper = new ModelMapper();
    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();


    @Mock
    UserRepository mockedUserRepository;
    @Mock
    UserRoleRepository mockedUserRoleRepository;
    @Mock
    AppUserServiceImpl mockedAppUserService;


    @BeforeEach
    void setUp(){
        this.userEntityTest = this.userInit(this.rolesInit());
        this.userTest = this.modelMapper.map(userEntityTest, UserServiceModel.class);
        this.userServiceTest = new UserServiceImpl(
                this.mockedUserRepository,
                modelMapper,
                passwordEncoder,
                mockedUserRoleRepository,
                mockedAppUserService
                );
    }

    @Test
    void shouldReturnCorrectUser(){
        Mockito.when(
                mockedUserRepository.findByUsername("UserTest"))
                .thenReturn(java.util.Optional.ofNullable(this.userEntityTest));
        UserServiceModel actual = userServiceTest.findUserByUsername("UserTest");

        Assertions.assertEquals(this.userTest.getUsername(), actual.getUsername());
    }


    private UserEntity userInit(UserRoleEntity userRoleEntity){
        return new UserEntity(
                "UserTest",
                "123456",
                List.of(userRoleEntity),
                LocalDateTime.now(),
                LocalDateTime.now()
        );
    }

    private UserRoleEntity rolesInit(){
        return new UserRoleEntity(UserRoleEnum.ROLE_ADMIN, "user");
    }
}
