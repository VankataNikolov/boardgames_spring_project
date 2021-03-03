package softuni.boardgames.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import softuni.boardgames.model.entity.GameEntity;

@Repository
public interface GameRepository extends JpaRepository<GameEntity, Long> {
}
