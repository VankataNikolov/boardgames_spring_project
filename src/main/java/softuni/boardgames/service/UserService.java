package softuni.boardgames.service;

import softuni.boardgames.model.service.UserServiceModel;

public interface UserService {
    void seedUsers();

    UserServiceModel findUserByUsername(String username);

    void registerAndLogin(UserServiceModel userServiceModel);
}
