package tukorea.devhive.swapshopbackend.model.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tukorea.devhive.swapshopbackend.model.dao.Login;

@NoArgsConstructor
@Getter
@Setter
public class UserDTO {

    private Long userId;
    private String email;
    private String name;
    private String picture;

    @Builder
    public UserDTO(Long userId, String email, String name, String picture) {
        this.userId=userId;
        this.email = email;
        this.name = name;
        this.picture = picture;
    }

    public static UserDTO mapToDto(Login login){
        return UserDTO.builder()
                .userId(login.getId())
                .name(login.getName())
                .email(login.getEmail())
                .build();
    }
}
