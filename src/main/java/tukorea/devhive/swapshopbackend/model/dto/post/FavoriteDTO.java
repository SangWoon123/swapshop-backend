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
    private String nickName;

    @Builder
    public FavoriteDTO(Long postId, String nickName) {
        this.postId = postId;
        this.nickName = nickName;
    }
}
