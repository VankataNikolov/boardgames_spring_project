package softuni.boardgames.web;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import softuni.boardgames.UserInit;
import softuni.boardgames.repository.UserRepository;


import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
public class UserControllerTest {

    private static final String USER_CONTROLLER_PREFIX = "/users";

    private MockMvc mockMvc;
    private UserInit userInit;
    private UserRepository userRepository;

    @Autowired
    public UserControllerTest(MockMvc mockMvc, UserInit userInit,
                              UserRepository userRepository) {
        this.mockMvc = mockMvc;
        this.userInit = userInit;
        this.userRepository = userRepository;
    }


    @BeforeEach
    void setUp(){
        this.userInit.clear();
        this.init();
    }

    @Test
    void registerShouldReturnValidViewModel() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(
                USER_CONTROLLER_PREFIX + "/register"
        ))
                .andExpect(status().isOk())
                .andExpect(view().name("register"))
                .andExpect(model().attributeExists("userRegisterBindingModel"));
    }

    @Test
    void loginShouldReturnValidView() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(
                USER_CONTROLLER_PREFIX + "/login"
        ))
                .andExpect(status().isOk())
                .andExpect(view().name("login"));
    }

    @Test
    void registerConfirmShouldRegisterAndRedirect() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post(
                USER_CONTROLLER_PREFIX + "/register"
        )
                .param("username", "test2")
                .param("password", "123456")
                .param("confirmPassword", "123456")
                .with(csrf())
        )
                .andExpect(status().is3xxRedirection());

        Assertions.assertEquals(2, userRepository.count());
    }

    @Test
    void registerConfirmShouldRedirectToRegisterFormWithInvalidParams() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post(
                USER_CONTROLLER_PREFIX + "/register"
        )
                .param("username", "az")
                .param("password", "123")
                .param("confirmPassword", "123")
                .with(csrf())
        )
                .andExpect(status().is3xxRedirection())
                .andExpect(flash().attributeCount(2))
                .andExpect(view().name("redirect:register"));

        Assertions.assertEquals(1, userRepository.count());
    }

    @Test
    void registerConfirmShouldRedirectNotMatchingPassword() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post(
                USER_CONTROLLER_PREFIX + "/register"
                )
                        .param("username", "azazov")
                        .param("password", "123457")
                        .param("confirmPassword", "123456")
                        .with(csrf())
        )
                .andExpect(status().is3xxRedirection())
                .andExpect(flash().attributeCount(1))
                .andExpect(view().name("redirect:register"));

        Assertions.assertEquals(1, userRepository.count());
    }

    @Test
    @WithMockUser(value = "UserTest", roles = {"ADMIN"})
    void changeRoleShouldReturnViewModel() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(
                USER_CONTROLLER_PREFIX + "/change-role"
        ))
                .andExpect(status().isOk())
                .andExpect(view().name("user-change-role"))
                .andExpect(model().attributeExists("allUsers", "userRoles"));
    }

    private void init() {
        this.userInit.rolesInit();
        this.userInit.userInit();
    }

}
