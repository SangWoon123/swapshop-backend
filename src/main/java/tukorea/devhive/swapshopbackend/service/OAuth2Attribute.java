package tukorea.devhive.swapshopbackend.service;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import tukorea.devhive.swapshopbackend.model.Enum.AuthenticationType;
import tukorea.devhive.swapshopbackend.model.dao.Login;

import java.util.HashMap;
import java.util.Map;

@ToString
@Builder(access = AccessLevel.PRIVATE)
@Getter
public class OAuth2Attribute {
    private Map<String, Object> attributes;
    private String attributeKey;
    private String email;
    private String name;
    private String picture;

    static OAuth2Attribute of(String provider, String attributeKey,
                              Map<String, Object> attributes) {
        switch (provider) {
//            case "google":
//                return ofGoogle(attributeKey, attributes);
            case "kakao":
                return ofKakao("email", attributes);
            default:
                throw new RuntimeException();
        }
    }

    private static OAuth2Attribute ofKakao(String attributeKey,
                                           Map<String, Object> attributes) {
        Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");
        Map<String, Object> kakaoProfile = (Map<String, Object>) kakaoAccount.get("profile");


        return OAuth2Attribute.builder()
                .name((String) kakaoProfile.get("nickname"))
                .email((String) kakaoAccount.get("email"))
                .picture((String)kakaoAccount.get("profile_image_url"))
                .attributes(attributes)
                .attributeKey(attributeKey)
                .build();
    }

    public Login toEntity() {
        return Login.builder()
                .email(this.email)
                .name(this.name)
                .authType(AuthenticationType.KAKAO)
                .token(this.attributeKey)
                .build();
    }




}
