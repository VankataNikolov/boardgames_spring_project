package softuni.boardgames.service;

import softuni.boardgames.model.service.UserRoleServiceModel;

import java.util.List;

public interface UserRoleService {
    void initRoles();

    List<UserRoleServiceModel> getAllRoles();
}
