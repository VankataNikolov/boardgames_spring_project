package softuni.boardgames.model.entity;

import softuni.boardgames.model.enums.GameCategoriesEnum;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

@Entity
@Table(name = "categories")
public class CategoryEntity extends BaseEntity{

    private GameCategoriesEnum name;

    public CategoryEntity() {
    }

    public CategoryEntity(GameCategoriesEnum name) {
        this.name = name;
    }

    @Enumerated(EnumType.STRING)
    public GameCategoriesEnum getName() {
        return name;
    }

    public void setName(GameCategoriesEnum name) {
        this.name = name;
    }
}
