package tukorea.devhive.swapshopbackend.model.dao.comment;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import tukorea.devhive.swapshopbackend.model.category.Category;
import tukorea.devhive.swapshopbackend.model.dao.BaseTimeEntity;
import tukorea.devhive.swapshopbackend.model.dao.login.Login;
import tukorea.devhive.swapshopbackend.model.dao.post.Post;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Comment extends BaseTimeEntity {

    @Id
    @GeneratedValue
    @Column(name = "comment_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Login login;  // 작성자(user)

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @Column(name = "comment_content", nullable = false)
    private String content;  // 댓글 내용

    @ManyToOne
    @JoinColumn(name = "parent_commnet_id")
    private Comment parentComment;  // 부모 Comment

    @OneToMany(mappedBy = "parentComment", cascade = CascadeType.ALL)
    private List<Comment> commentList = new ArrayList<>();  // 자식 Comment들

    @Builder
    public Comment(Long id, Post post, Login login, String content, Comment parentComment) {
        this.id = id;
        this.post = post;
        this.login = login;
        this.content = content;
        this.parentComment = parentComment;
    }

    // 댓글 수정
    public void update(String content) {
        this.content = content;
    }

    public void setCategory(Category category) {
    }

    public void setParentComment(Comment parentComment) {
        this.parentComment = parentComment;
    }

    public void addChildComment(Comment childComment) {
        this.commentList.add(childComment);
    }
}
