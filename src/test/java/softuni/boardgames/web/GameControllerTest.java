package softuni.boardgames.web;

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

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
public class GameControllerTest {

    private static final String GAME_CONTROLLER_PREFIX = "/games";

    private MockMvc mockMvc;
    private UserInit userInit;

    @Autowired
    public GameControllerTest(MockMvc mockMvc, UserInit userInit) {
        this.mockMvc = mockMvc;
        this.userInit = userInit;
    }

    @BeforeEach
    void setUp(){
        this.userInit.clear();
        this.init();
    }

    @Test
    void allGamesShouldReturnViewModel() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get(GAME_CONTROLLER_PREFIX + "/all")
        )
                .andExpect(status().isOk())
                .andExpect(view().name("index"));
    }

    @Test
    void addShouldRedirectWithoutAuthorizedUser() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get(GAME_CONTROLLER_PREFIX + "/add")
        )
                .andExpect(status().is3xxRedirection());
    }

    @Test
    @WithMockUser(value = "UserTest", roles = {"EDITOR"})
    void addShouldReturnViewAndModel() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get(GAME_CONTROLLER_PREFIX + "/add")
        )
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("gameAddBindingModel", "allCategories"))
                .andExpect(view().name("games-add"));
    }

    private void init() {
        this.userInit.rolesInit();
        this.userInit.userInit();
    }
}
