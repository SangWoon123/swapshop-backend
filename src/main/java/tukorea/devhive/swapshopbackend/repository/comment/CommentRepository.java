package tukorea.devhive.swapshopbackend.repository.comment;

import org.springframework.data.jpa.repository.JpaRepository;
import tukorea.devhive.swapshopbackend.model.dao.comment.Comment;

import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    Optional<Object> findByPostIdAndId(Long postId, Long id);
}