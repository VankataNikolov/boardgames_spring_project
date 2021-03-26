package softuni.boardgames.model.service;

import softuni.boardgames.model.enums.UserRoleEnum;

public class UserRoleServiceModel {

    private UserRoleEnum role;
    private String description;

    public UserRoleServiceModel() {
    }

    public UserRoleEnum getRole() {
        return role;
    }

    public void setRole(UserRoleEnum role) {
        this.role = role;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
