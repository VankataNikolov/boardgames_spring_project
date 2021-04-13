package softuni.boardgames.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import softuni.boardgames.model.entity.GameStatisticEntity;

@Repository
public interface GameStatisticRepository extends JpaRepository<GameStatisticEntity, String> {
    GameStatisticEntity findByName(String gameName);
}
