package softuni.boardgames.model.service;

import softuni.boardgames.model.entity.CategoryEntity;
import softuni.boardgames.model.entity.GameImagesEntity;

import java.time.LocalDateTime;
import java.util.List;

public class GameServiceModel {

    private Long id;
    private String name;
    private String description;
    private String titleImgUrl;
    private List<CategoryEntity> categories;
    private LocalDateTime createdOn;
    private LocalDateTime lastEdited;
    private UserServiceModel addedBy;
    private List<GameImagesEntity> pictures;

    public GameServiceModel() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitleImgUrl() {
        return titleImgUrl;
    }

    public void setTitleImgUrl(String titleImgUrl) {
        this.titleImgUrl = titleImgUrl;
    }

    public List<CategoryEntity> getCategories() {
        return categories;
    }

    public void setCategories(List<CategoryEntity> categories) {
        this.categories = categories;
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

    public UserServiceModel getAddedBy() {
        return addedBy;
    }

    public void setAddedBy(UserServiceModel addedBy) {
        this.addedBy = addedBy;
    }

    public List<GameImagesEntity> getPictures() {
        return pictures;
    }

    public void setPictures(List<GameImagesEntity> pictures) {
        this.pictures = pictures;
    }
}
