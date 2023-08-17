package tukorea.devhive.swapshopbackend.model.dto.comment;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.minidev.json.annotate.JsonIgnore;
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

    private String nickname;
    private String content;
    private Comment parentComment;
    private Long parentCommentId;

    @Builder
    public CommentDTO(Long id, Post post, String content) {
        this.id = id;
        this.post = post;
        this.content = content;
    }

    @Builder
    public static CommentDTO createCommentDto(Comment comment) {
        return new CommentDTO(
                comment.getId(),
                comment.getPost(),
                comment.getContent()
        );
    }

    public Comment toEntity(){
        return Comment.builder()
                .id(id)
                .content(content)
                .build();
    }
}
