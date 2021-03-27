package softuni.boardgames.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import softuni.boardgames.model.entity.CategoryEntity;
import softuni.boardgames.model.entity.GameEntity;

import java.util.List;

@Repository
public interface GameRepository extends JpaRepository<GameEntity, Long> {

    List<GameEntity> findAllByCategoriesContains(CategoryEntity categoryEntity);
}
