package softuni.boardgames.data_init;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import softuni.boardgames.service.CategoryService;
import softuni.boardgames.service.LinkService;
import softuni.boardgames.service.UserRoleService;
import softuni.boardgames.service.UserService;

@Component
public class DataInitialize implements CommandLineRunner {

    private final UserRoleService userRoleService;
    private final CategoryService categoryService;
    private final LinkService linkService;
    private final UserService userService;

    public DataInitialize(UserRoleService userRoleService,
                          CategoryService categoryService,
                          LinkService linkService,
                          UserService userService) {
        this.userRoleService = userRoleService;
        this.categoryService = categoryService;
        this.linkService = linkService;
        this.userService = userService;
    }

    @Override
    public void run(String... args) throws Exception {
        userRoleService.initRoles();
        categoryService.initCategories();
        userService.seedUsers();
        linkService.seedLinks();
    }
}
