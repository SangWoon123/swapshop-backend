package tukorea.devhive.swapshopbackend.model.dto;

import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class UserRequestMapper {
    public UserDTO toDto(OAuth2User oAuth2User){

        var attributes=oAuth2User.getAttributes();

        // 카카오일 경우
        if(attributes.containsKey("kakao_account")){
            Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");

            return UserDTO.builder()
                    .email((String) kakaoAccount.get("email"))
                    .name((String) kakaoAccount.get("nickname"))
                    .build();
        }

        // 구글일 경우
        return UserDTO.builder()
                .email((String) attributes.get("email"))
                .name((String) attributes.get("name"))
                //.picture((String) attributes.get("picture"))
                .build();
    }
}
