package softuni.boardgames.model.entity;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Size;

@Entity
@Table(name = "game_images")
public class GameImagesEntity extends BaseEntity{

    private String url;
    private GameEntity game;

    public GameImagesEntity() {
    }

    public GameImagesEntity(String url, GameEntity game) {
        this.url = url;
        this.game = game;
    }

    @Size(max = 255)
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @ManyToOne
    public GameEntity getGame() {
        return game;
    }

    public void setGame(GameEntity game) {
        this.game = game;
    }
}
