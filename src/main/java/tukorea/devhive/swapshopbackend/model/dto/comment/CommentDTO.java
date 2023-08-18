package tukorea.devhive.swapshopbackend.model.dto.comment;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import tukorea.devhive.swapshopbackend.model.dao.comment.Comment;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class CommentDTO {
    private Long id;
    private Long postId;
    private String nickname;
    private String content;
    private Long parentCommentId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
//    private Comment parentComment;

    @Builder
    public CommentDTO(Long id, Long postId, String content, String nickname, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.postId = postId;
        this.content = content;
        this.nickname = nickname;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;

    }

    public static CommentDTO createCommentDto(Comment comment) {
        return new CommentDTO(
                comment.getId(),
                comment.getPost().getId(),
                comment.getContent(),
                comment.getNickname(),
                comment.getCreatedDate(),
                comment.getUpdatedDate()
        );
    }

    // 이런 식으로 코드 짜면 더 가독성 있음
//    public static CommentDTO createCommentDto(Comment comment) {
//        return CommentDTO.builder()
//                .id(comment.getId())
//                .postId(comment.getPost().getId())
//                .build()
//    }

    public Comment toEntity(){
        return Comment.builder()
                .id(id)
                .content(content)
                .build();
    }
}
