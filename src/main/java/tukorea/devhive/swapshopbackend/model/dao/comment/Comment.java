package tukorea.devhive.swapshopbackend.model.dao.comment;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import tukorea.devhive.swapshopbackend.model.dao.BaseTimeEntity;
import tukorea.devhive.swapshopbackend.model.dao.login.Login;
import tukorea.devhive.swapshopbackend.model.dao.post.Post;

import javax.persistence.*;

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

    @Column(name = "comment_content", nullable = false)
    private String content;  // 댓글 내용



    @Builder
    public Comment(Long id, Post post, Login login, String content, String createdAt, String updatedAt) {
        this.id = id;
        this.post = post;
        this.login = login;
        this.content = content;
    }

    // 댓글 수정
    public void update(String content) {
        this.content = content;
    }
}
