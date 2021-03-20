package softuni.boardgames.model.service;

import java.time.LocalDateTime;

public class CommentServiceModel {

    private String title;
    private String content;
    private LocalDateTime createdOn;
    private LocalDateTime lastEdited;
    private Long game;
    private String author;

    public CommentServiceModel() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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

    public Long getGame() {
        return game;
    }

    public void setGame(Long game) {
        this.game = game;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
