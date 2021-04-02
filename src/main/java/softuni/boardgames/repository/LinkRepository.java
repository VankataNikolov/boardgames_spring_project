package softuni.boardgames.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import softuni.boardgames.model.entity.LinkEntity;

@Repository
public interface LinkRepository extends JpaRepository<LinkEntity, Long> {
}
