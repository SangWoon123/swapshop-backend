package tukorea.devhive.swapshopbackend.model.dto.login;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import tukorea.devhive.swapshopbackend.model.Enum.login.AuthenticationType;
import tukorea.devhive.swapshopbackend.model.dao.login.Login;

@Getter
@NoArgsConstructor
public class LoginDTO {
    private Long userId;
    private String email;
    private String nickname;
    private String password;
    private String major;
    private String introduction;
    private AuthenticationType authenticationType;

    @Builder
    public LoginDTO(Long userId, String email, String nickname, String password, String major, String introduction, AuthenticationType authenticationType) {
        this.userId = userId;
        this.email = email;
        this.nickname = nickname;
        this.password = password;
        this.major = major;
        this.introduction = introduction;
        this.authenticationType = authenticationType;
    }

    public static LoginDTO mapToDto(Login login){
        return LoginDTO.builder()
                .userId(login.getId())
                .email(login.getEmail())
                .nickname(login.getNickname())
                .major(login.getMajor())
                .introduction(login.getIntroduction())
                .authenticationType(login.getAuthType())
                .build();
    }
}
