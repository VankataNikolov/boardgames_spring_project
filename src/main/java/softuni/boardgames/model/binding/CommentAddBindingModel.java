package softuni.boardgames.model.binding;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class CommentAddBindingModel {

    private String title;
    private String content;

    public CommentAddBindingModel() {
    }

    @NotBlank
    @Size(min = 5, max = 100, message = "title must be between 5 and 100 characters")
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @NotBlank
    @Size(min = 10, message = "text must be minimum 10 characters")
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
