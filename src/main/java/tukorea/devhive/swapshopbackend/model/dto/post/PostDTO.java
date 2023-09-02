package tukorea.devhive.swapshopbackend.model.dto.post;

import lombok.*;
import tukorea.devhive.swapshopbackend.model.Enum.post.TradeStatus;
import tukorea.devhive.swapshopbackend.model.dao.TradePeriod;
import tukorea.devhive.swapshopbackend.model.dao.post.Favorite;
import tukorea.devhive.swapshopbackend.model.dto.CategoryDTO;
import tukorea.devhive.swapshopbackend.model.dto.comment.CommentDTO;

import java.time.LocalDateTime;
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
    //private String location;
   // private TradePeriod desiredTime;
    //private TradeStatus status;
    private int views;
    private LocalDateTime createdDate;

    // 이미지 정보
    private List<ImageDTO> images;

    // 카테고리
    private CategoryDTO category;

    // 댓글
    private List<CommentDTO> comment;

    //좋아요
    private List<FavoriteDTO> favorite;

    // 게시물 생성시 보여지는 메인화면에 필요한 dto클래스 정의
    @Data
    @Builder
    public static class PostMain{

        private Long id;

        private Long userId;
        private String nickname;
        private String major;

        private String title;
        private String content;
        private LocalDateTime createdDate;
        private int price;
        private List<ImageDTO> images;
        //private int favorite;

        // 댓글
        private List<CommentDTO> comment;

        //좋아요
        private List<FavoriteDTO> favorite;


        public PostMain(Long id, Long userId, String nickname, String major, String title, String content, LocalDateTime createdDate, int price, List<ImageDTO> images, List<CommentDTO> comment, List<FavoriteDTO> favorite) {
            this.id = id;
            this.userId = userId;
            this.nickname = nickname;
            this.major = major;
            this.title = title;
            this.content = content;
            this.createdDate = createdDate;
            this.price = price;
            this.images = images;
            this.comment = comment;
            this.favorite = favorite;
        }
    }
}
