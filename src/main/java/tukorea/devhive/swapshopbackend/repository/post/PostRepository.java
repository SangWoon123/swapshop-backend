package tukorea.devhive.swapshopbackend.repository.post;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tukorea.devhive.swapshopbackend.model.dao.post.Post;
import tukorea.devhive.swapshopbackend.model.dao.login.Login;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post,Long> {

    List<Post> findByLogin(Login login);

    // 댓글 정보 포함하여 조회
    @EntityGraph(attributePaths = "comments")
    Optional<Post> findWithCommentsById(Long id);

    @Modifying
    @Query("update Post p set p.views = p.views+1 where p.id=:id")
    int updateViews(@Param("id") Long id);
}
