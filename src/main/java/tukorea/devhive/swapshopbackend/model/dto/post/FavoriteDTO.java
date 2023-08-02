package tukorea.devhive.swapshopbackend.model.dto.post;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tukorea.devhive.swapshopbackend.model.dao.login.Login;
import tukorea.devhive.swapshopbackend.model.dao.post.Post;
import tukorea.devhive.swapshopbackend.model.dto.login.LoginDTO;

@Getter
@Setter
@NoArgsConstructor
public class FavoriteDTO {
    private Long postId;

    @Builder
    public FavoriteDTO(Long postId) {
        this.postId = postId;
    }
}
