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

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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
    private String createdAt = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm"));
    private String updatedAt = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm"));

    @Builder
    public CommentDTO(Long id, Post post, Login login, Category category, String nickname, String content, String createdAt, String updatedAt) {
        this.id = id;
        this.post = post;
        this.login = login;
        this.category = category;
        this.nickname = getLogin().getNickname();
        this.content = content;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    // DTO -> Entity
    public Comment toEntity(){
        return Comment.builder()
                .id(id)
                .post(post)
                .login(login)
                .content(content)
                .createdAt(createdAt)
                .updatedAt(updatedAt)
                .build();
    }

}
