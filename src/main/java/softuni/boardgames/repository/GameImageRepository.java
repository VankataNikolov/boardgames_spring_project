package softuni.boardgames.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import softuni.boardgames.model.entity.GameImagesEntity;

@Repository
public interface GameImageRepository extends JpaRepository<GameImagesEntity, Long> {
}
