package softuni.boardgames.model.entity;

import softuni.boardgames.model.enums.UserRoleEnum;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Table(name = "roles")
public class UserRoleEntity extends BaseEntity{

    private UserRoleEnum role;
    private String description;

    public UserRoleEntity() {
    }

    public UserRoleEntity(UserRoleEnum role, String description) {
        this.role = role;
        this.description = description;
    }

    @Enumerated(EnumType.STRING)
    public UserRoleEnum getRole() {
        return role;
    }

    public void setRole(UserRoleEnum role) {
        this.role = role;
    }

    @Size(max = 100)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
