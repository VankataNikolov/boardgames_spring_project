package softuni.boardgames.model.binding;

import javax.validation.constraints.Size;

public class GameInitBindingModel {

    private String name;
    private String description;
    private String titleImgUrl;
    private String[] categories;

    public GameInitBindingModel() {
    }

    @Size(min = 2, max = 100)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Size(min = 10)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Size(max = 255)
    public String getTitleImgUrl() {
        return titleImgUrl;
    }

    public void setTitleImgUrl(String titleImgUrl) {
        this.titleImgUrl = titleImgUrl;
    }

    public String[] getCategories() {
        return categories;
    }

    public void setCategories(String[] categories) {
        this.categories = categories;
    }
}
