package tukorea.devhive.swapshopbackend.model.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class UserDto {

    private Long userId;
    private String email;
    private String name;
    private String picture;

    @Builder
    public UserDto(Long id,String email, String name, String picture) {
        this.userId=id;
        this.email = email;
        this.name = name;
        this.picture = picture;
    }
}
