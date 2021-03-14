package softuni.boardgames.model.entity;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "users")
public class UserEntity extends BaseEntity {

    private String username;
    private String password;
    private List<UserRoleEntity> roles;
    private LocalDateTime createdOn;
    private LocalDateTime lastEdited;

    public UserEntity() {
    }

    public UserEntity(String username,
                      String password,
                      List<UserRoleEntity> roles,
                      LocalDateTime createdOn,
                      LocalDateTime lastEdited) {
        this.username = username;
        this.password = password;
        this.roles = roles;
        this.createdOn = createdOn;
        this.lastEdited = lastEdited;
    }

    @Size(min = 4, max = 20)
    @Column(nullable = false, unique = true)
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Size(max = 255)
    @Column(nullable = false)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @ManyToMany(fetch = FetchType.EAGER)
    public List<UserRoleEntity> getRoles() {
        return roles;
    }

    public void setRoles(List<UserRoleEntity> roles) {
        this.roles = roles;
    }

    @Column(name = "created_on")
    public LocalDateTime getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(LocalDateTime createdOn) {
        this.createdOn = createdOn;
    }

    @Column(name = "last_edited")
    public LocalDateTime getLastEdited() {
        return lastEdited;
    }

    public void setLastEdited(LocalDateTime lastEdited) {
        this.lastEdited = lastEdited;
    }
}
