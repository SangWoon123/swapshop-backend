package tukorea.devhive.swapshopbackend.model.dto.post;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ImageDTO {
    @JsonIgnore
    private Long id;
    private String filePath;

    @Builder
    public ImageDTO(Long id, String filePath) {
        this.id = id;
        this.filePath = filePath;
    }
}
