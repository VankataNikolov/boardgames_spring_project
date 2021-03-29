package softuni.boardgames;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class BoardgamesApplication {

    public static void main(String[] args) {
        SpringApplication.run(BoardgamesApplication.class, args);
    }

}
