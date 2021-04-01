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
import softuni.boardgames.UnitTestUserInit;
import softuni.boardgames.model.entity.UserEntity;
import softuni.boardgames.model.entity.UserRoleEntity;
import softuni.boardgames.model.enums.UserRoleEnum;
import softuni.boardgames.model.service.UserServiceModel;
import softuni.boardgames.repository.UserRepository;
import softuni.boardgames.repository.UserRoleRepository;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {

    private UnitTestUserInit unitTestUserInit;
    private UserServiceImpl userServiceTest;
    private UserEntity userEntityTest;
    private ModelMapper modelMapper;
    private PasswordEncoder passwordEncoder;


    @Mock
    UserRepository mockedUserRepository;
    @Mock
    UserRoleRepository mockedUserRoleRepository;
    @Mock
    AppUserServiceImpl mockedAppUserService;

    public UserServiceImplTest() {
        this.unitTestUserInit = new UnitTestUserInit();
        this.modelMapper = new ModelMapper();
        this.passwordEncoder = new BCryptPasswordEncoder();
    }


    @BeforeEach
    void setUp(){
        this.userEntityTest = this.unitTestUserInit.userEntityInit();
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

        Assertions.assertEquals(this.userEntityTest.getUsername(), actual.getUsername());
    }

    @Test
    void getAllShouldReturnCorrectListOfUsers(){
        UserEntity secondUserEntityTest = this.unitTestUserInit.secondUserEntityInit();
        Mockito.when(
                mockedUserRepository.findAll())
                .thenReturn(List.of(userEntityTest, secondUserEntityTest));
        List<UserServiceModel> actual = userServiceTest.getAllUsers();
        Assertions.assertEquals(2, actual.size());
        Assertions.assertEquals(userEntityTest.getUsername(), actual.get(0).getUsername());
        Assertions.assertEquals(secondUserEntityTest.getUsername(), actual.get(1).getUsername());
    }

    @Test
    void testChangeRoles(){
        UserEntity secondUserEntityTest = unitTestUserInit.secondUserEntityInit();
        UserRoleEntity newUserRoleEntity = new UserRoleEntity(UserRoleEnum.ROLE_USER, "user");
        Mockito.when(
                mockedUserRepository.findByUsername("UserTest2"))
                .thenReturn(java.util.Optional.ofNullable(secondUserEntityTest));
        Mockito.when(
                mockedUserRoleRepository.findByRole(UserRoleEnum.ROLE_USER))
                .thenReturn(newUserRoleEntity);
        Mockito.when(mockedUserRepository.save(any()))
                .thenReturn(any());

        userServiceTest.changeRoles("UserTest2", "ROLE_USER");

        Mockito.verify(mockedUserRepository, times(1))
                .save(any());
        assert secondUserEntityTest != null;
        Assertions.assertEquals(1, secondUserEntityTest.getRoles().size());
    }

}
