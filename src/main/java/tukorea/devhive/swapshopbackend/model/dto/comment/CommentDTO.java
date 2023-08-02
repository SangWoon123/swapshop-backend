package tukorea.devhive.swapshopbackend.model.dto.comment;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.minidev.json.annotate.JsonIgnore;
import tukorea.devhive.swapshopbackend.model.category.Category;
import tukorea.devhive.swapshopbackend.model.dao.comment.Comment;
import tukorea.devhive.swapshopbackend.model.dao.login.Login;
import tukorea.devhive.swapshopbackend.model.dao.post.Post;

@Getter
@Setter
@NoArgsConstructor
public class CommentDTO {
    private Long id;

    @JsonIgnore
    private Post post;
    private Login login;
    private Category category;

    private String nickname;
    private String content;
    private Comment parentComment;
    private Long parentCommentId;

    @Builder
    public CommentDTO(Long id, Post post, Login login, Category category, String nickname,
                      String content, Comment parentComment, Long parentCommentId) {
        this.id = id;
        this.post = post;
        this.login = login;
        this.category = category;
        this.nickname = getLogin().getNickname();
        this.content = content;
        this.parentComment = parentComment;
        this.parentCommentId = parentCommentId;
    }

    // DTO -> Entity
    public Comment toEntity(){
        return Comment.builder()
                .id(id)
                .post(post)
                .login(login)
                .parentComment(parentComment)
                .content(content)
                .build();
    }

}
