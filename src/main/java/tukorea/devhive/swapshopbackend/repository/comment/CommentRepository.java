package tukorea.devhive.swapshopbackend.repository.comment;

import org.springframework.data.jpa.repository.JpaRepository;
import tukorea.devhive.swapshopbackend.model.dao.comment.Comment;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByPostId(Long postId);
}