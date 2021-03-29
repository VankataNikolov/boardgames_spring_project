package softuni.boardgames.web;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
public class HomeControllerTest {

    private static final String HOME_CONTROLLER_PREFIX = "/";

    @Autowired
    private MockMvc mockMvc;

    @Test
    void indexShouldReturnCorrectView() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get(HOME_CONTROLLER_PREFIX)
        )
                .andExpect(status().isOk())
                .andExpect(view().name("index"));
    }

    @Test
    void homeShouldRedirectLoginWithoutLoggedUser() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/home")
        )
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost/users/login"));
    }
}
