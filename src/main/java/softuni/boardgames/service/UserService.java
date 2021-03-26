package softuni.boardgames.service;

import softuni.boardgames.model.service.UserServiceModel;

import java.util.List;

public interface UserService {
    void seedUsers();

    UserServiceModel findUserByUsername(String username);

    void registerAndLogin(UserServiceModel userServiceModel);

    List<UserServiceModel> getAllUsers();

    void changeRoles(String username, String userRoleName);
}
