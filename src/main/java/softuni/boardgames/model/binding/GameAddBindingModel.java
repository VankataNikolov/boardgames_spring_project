package softuni.boardgames.model.binding;

import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.List;

public class GameAddBindingModel {

    private String name;
    private MultipartFile titleImgUrl;
    private String description;
    private List<String> categories;
    private List<MultipartFile> pictures;

    public GameAddBindingModel() {
    }

    @Size(min = 2, max = 100, message = "Name must be between 2 and 100 characters")
    @NotBlank
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public MultipartFile getTitleImgUrl() {
        return titleImgUrl;
    }

    public void setTitleImgUrl(MultipartFile titleImgUrl) {
        this.titleImgUrl = titleImgUrl;
    }

    @Size(min = 10, message = "Description must be minimum 10 characters")
    @NotBlank
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @NotEmpty(message = "choose at least one category")
    public List<String> getCategories() {
        return categories;
    }

    public void setCategories(List<String> categories) {
        this.categories = categories;
    }

    public List<MultipartFile> getPictures() {
        return pictures;
    }

    public void setPictures(List<MultipartFile> pictures) {
        this.pictures = pictures;
    }

    @Override
    public String toString() {
        return "GameAddBindingModel{" +
                "name='" + name + '\'' +
                ", titleImgUrl=" + titleImgUrl +
                ", description='" + description + '\'' +
                ", categories=" + categories +
                ", pictures=" + pictures +
                '}';
    }
}
