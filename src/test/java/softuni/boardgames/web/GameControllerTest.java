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
import softuni.boardgames.repository.GameRepository;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
public class GameControllerTest {

    private static final String GAME_CONTROLLER_PREFIX = "/games";

    private MockMvc mockMvc;
    private UserInit userInit;
    private GameEntitiesInit gameEntitiesInit;
    private GameRepository gameRepository;

    @Autowired
    public GameControllerTest(MockMvc mockMvc,
                              UserInit userInit,
                              GameEntitiesInit gameEntitiesInit,
                              GameRepository gameRepository) {
        this.mockMvc = mockMvc;
        this.userInit = userInit;
        this.gameEntitiesInit = gameEntitiesInit;
        this.gameRepository = gameRepository;
    }

    @BeforeEach
    void setUp(){
        this.gameEntitiesInit.clear();
        this.userInit.clear();
        this.init();
    }


    @Test
    void allGamesShouldReturnViewAndModel() throws Exception {

        mockMvc.perform(
                MockMvcRequestBuilders.get(GAME_CONTROLLER_PREFIX + "/all")
        )
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("allGames", "allCategories"))
                .andExpect(view().name("games-all"));

    }

    @Test
    void addShouldRedirectWithoutAuthorizedUser() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get(GAME_CONTROLLER_PREFIX + "/add")
        )
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost/users/login"));
    }

    @Test
    @WithMockUser(value = "Test", roles = {"EDITOR"})
    void addShouldReturnViewAndModel() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get(GAME_CONTROLLER_PREFIX + "/add")
        )
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("gameAddBindingModel", "allCategories"))
                .andExpect(view().name("games-add"));
    }

    @Test
    @WithMockUser(value = "Test", roles = {"EDITOR", "ADMIN"})
    void detailsShouldReturnCorrectGameViewAndModel() throws Exception {

        long gameId = gameRepository.findByName("GameTest1").getId();
        mockMvc.perform(
                MockMvcRequestBuilders.get(GAME_CONTROLLER_PREFIX + "/" + gameId + "/details")
        )
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("gameDetails"))
                .andExpect(view().name("games-details"));

    }

    private void init() {
        this.userInit.roleEntitiesInit();
        this.userInit.userEntityInitAndSave();
        this.gameEntitiesInit.init();
    }
}
