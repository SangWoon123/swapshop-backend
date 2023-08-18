package tukorea.devhive.swapshopbackend.model.dto.post;

import lombok.*;
import tukorea.devhive.swapshopbackend.model.Enum.post.TradeStatus;
import tukorea.devhive.swapshopbackend.model.dao.TradePeriod;
import tukorea.devhive.swapshopbackend.model.dto.CategoryDTO;
import tukorea.devhive.swapshopbackend.model.dto.comment.CommentDTO;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class PostDTO {
    private Long id;

    // 글 작성시 프론트에게 넘겨줄 유저 정보
    private Long userId;
    private String nickname;
    private String major;

    // 게시물 생성시 전달할 게시물 정보
    private String title;
    private String content;
    private int price;
    private String location;
    private TradePeriod desiredTime;
    private TradeStatus status;
    private int views;

    // 이미지 정보
    private List<ImageDTO> images;

    // 카테고리
    private CategoryDTO category;

    // 댓글
    private List<CommentDTO> comment;
}
