package softuni.boardgames.model.view;


import softuni.boardgames.model.enums.GameCategoriesEnum;

import java.util.List;

public class GameAllViewModel {

    private Long id;
    private String name;
    private String titleImgUrl;
    private String description;
    private List<GameCategoriesEnum> categories;

    public GameAllViewModel() {
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

    public String getTitleImgUrl() {
        return titleImgUrl;
    }

    public void setTitleImgUrl(String titleImgUrl) {
        this.titleImgUrl = titleImgUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<GameCategoriesEnum> getCategories() {
        return categories;
    }

    public void setCategories(List<GameCategoriesEnum> categories) {
        this.categories = categories;
    }
}
