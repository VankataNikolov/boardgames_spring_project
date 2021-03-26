package softuni.boardgames.model.binding;

import javax.validation.constraints.NotEmpty;


public class UserChangeRoleBindingModel {

    private String username;
    private String userRole;

    public UserChangeRoleBindingModel() {
    }

    @NotEmpty(message = "please select a user")
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @NotEmpty(message = "please select a user")
    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }
}
