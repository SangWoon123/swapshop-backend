package tukorea.devhive.swapshopbackend.model.dao.comment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import tukorea.devhive.swapshopbackend.model.dao.BaseTimeEntity;
import tukorea.devhive.swapshopbackend.model.dao.post.Post;
import tukorea.devhive.swapshopbackend.model.dto.comment.CommentDTO;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Comment extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    @Column(name = "comment_content", nullable = true)
    private String content;  // 댓글 내용

    private String nickname;

    @ManyToOne
    @JoinColumn(name = "parent_comment_id")
    private Comment parentComment;  // 부모 Comment

    @OneToMany(mappedBy = "parentComment", cascade = CascadeType.ALL)
    private List<Comment> commentList = new ArrayList<>();  // 자식 Comment들


    public static Comment createComment(CommentDTO dto, Post post) {

        if (dto.getId() != null)
            throw new IllegalArgumentException("댓글 생성 실패 : 댓글의 id가 없어야 합니다.");

        // Entity 생성 및 반환
        return Comment.builder()
                .nickname(dto.getNickname())
                .content(dto.getContent())
                .post(post)
                .build();
    }

    public static Comment createReply(CommentDTO dto, Post post, Comment parentComment) {
        if (dto.getId() != null)
            throw new IllegalArgumentException("대댓글 생성 실패 : 대댓글의 id가 없어야 합니다.");

//        post = parentComment.getPost();

        return Comment.builder()
                .post(post)
                .parentComment(parentComment)
                .nickname(dto.getNickname())
                .content(dto.getContent())
                .build();
    }

    public void patch(CommentDTO dto) {
        if (this.id != dto.getId())
            throw new IllegalArgumentException("댓글 수정 실패 : 잘못된 id가 입력되었습니다.");

        if(dto.getContent() != null)
            this.content = dto.getContent();
    }
}
