package tukorea.devhive.swapshopbackend.repository.post;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tukorea.devhive.swapshopbackend.model.dao.post.Post;
import tukorea.devhive.swapshopbackend.model.dao.login.Login;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post,Long> {

    List<Post> findByLogin(Login login);

    @Modifying
    @Query("update Post p set p.views = p.views+1 where p.id=:id")
    int updateViews(@Param("id") Long id);
}
