package tukorea.devhive.swapshopbackend.model.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tukorea.devhive.swapshopbackend.model.Enum.post.TradeStatus;
import tukorea.devhive.swapshopbackend.model.dto.login.LoginDTO;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class PostCategoryDTO {


    private Long id;
    private Long postId;
    private Long categoryId;

    @Builder
    public PostCategoryDTO(Long id, Long postId, Long categoryId) {
        this.id = id;
        this.postId = postId;
        this.categoryId = categoryId;
    }
}
