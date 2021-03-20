package softuni.boardgames.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import softuni.boardgames.model.entity.CommentEntity;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<CommentEntity, Long> {

    @Query("SELECT c FROM CommentEntity c WHERE c.game.id = :id ORDER BY c.createdOn asc" )
    List<CommentEntity> findAllByGameId(@Param("id") Long id);
}
