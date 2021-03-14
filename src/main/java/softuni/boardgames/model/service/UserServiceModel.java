package softuni.boardgames.model.service;

import javax.persistence.Column;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

public class UserServiceModel {

    private String username;
    private String password;
    private LocalDateTime createdOn;
    private LocalDateTime lastEdited;

    public UserServiceModel() {
    }

    @Size(min = 4, max = 20)
    @Column(nullable = false, unique = true)
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Size(min = 6, max = 20)
    @Column(nullable = false)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LocalDateTime getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(LocalDateTime createdOn) {
        this.createdOn = createdOn;
    }

    public LocalDateTime getLastEdited() {
        return lastEdited;
    }

    public void setLastEdited(LocalDateTime lastEdited) {
        this.lastEdited = lastEdited;
    }
}
