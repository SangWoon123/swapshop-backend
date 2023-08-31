package tukorea.devhive.swapshopbackend.model.dto.post;

import lombok.Builder;
import lombok.Data;

/**
 * 찜목록 추가할때 반환데이터로
 *
 * 찜목록에 추가되면 true를
 *
 * 찜목록에서 제거되면 false를 반환하는 찜 dto클래스
 **
 */
@Data
public class FavoriteSuccess {
    private boolean check;

    @Builder
    public FavoriteSuccess(boolean check) {
        this.check = check;
    }

}
