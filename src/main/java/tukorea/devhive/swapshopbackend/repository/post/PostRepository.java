package tukorea.devhive.swapshopbackend.repository.post;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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


    // 페이징 처리
    @Query(value = "SELECT p FROM Post p WHERE p.id < ?1 ORDER BY p.id desc ")
    Page<Post> findByPostIdLessThanOrderByPostIdDesc(Long lastPostId, PageRequest pageRequest);

    // 검색기능
    @Query("SELECT p FROM Post p WHERE p.title LIKE %:keyword% OR p.category.major LIKE %:keyword% OR p.category.professor LIKE %:keyword% OR p.category.name LIKE %:keyword%")
    List<Post> findByTitleContainingOrCategoryMajorContainingOrCategoryProfessorContainingOrCategoryNameContaining(@Param("keyword") String keyword);

    //정렬
    @Query("SELECT p FROM Post p WHERE p.title LIKE %:keyword% OR p.category.major LIKE %:keyword% OR p.category.professor LIKE %:keyword% OR p.category.name LIKE %:keyword%")
    List<Post> findByTitleContainingOrCategoryMajorContainingOrCategoryProfessorContainingOrCategoryNameContaining(@Param("keyword") String keyword, Sort sort);

}
