package tukorea.devhive.swapshopbackend.service;

import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.endpoint.OAuth2AccessTokenResponse;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import tukorea.devhive.swapshopbackend.model.Enum.AuthenticationType;
import tukorea.devhive.swapshopbackend.model.dao.Login;

import tukorea.devhive.swapshopbackend.repository.LoginRepository;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final LoginRepository loginRepository;
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2UserService<OAuth2UserRequest, OAuth2User> oAuth2UserService = new DefaultOAuth2UserService();

        OAuth2User oAuth2User = oAuth2UserService.loadUser(userRequest);

        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        String userNameAttributeName = userRequest.getClientRegistration()
                .getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();

        log.info("registrationId = {}", registrationId);
        log.info("userNameAttributeName = {}", userNameAttributeName);

        OAuth2Attribute oAuth2Attribute =
                OAuth2Attribute.of(registrationId, userNameAttributeName, oAuth2User.getAttributes());
        // registrationId -> ex) google, kakao

        String email=oAuth2Attribute.getEmail(); // 이메일로 DB저장여부 체크


        // registrationId 를 AuthType으로 변경
        AuthenticationType authTypeFromRegistrationId = getAuthTypeFromRegistrationId(registrationId);

        // ex) 카카오, 구글 등 플랫폼마다 같은 이메일로 가입한 경우 authType으로 구분하기
        if(loginRepository.findByEmailAndAuthType(email,authTypeFromRegistrationId).isEmpty()){
            Login login = oAuth2Attribute.toEntity(registrationId);
            loginRepository.save(login);
        }else{
            log.info("이미 회원 입니다.");
        }

        return oAuth2User;
    }

    private AuthenticationType getAuthTypeFromRegistrationId(String registrationId) {
        switch (registrationId){
            case "google":
                return AuthenticationType.GOOGLE;
            case "kakao":
                return AuthenticationType.KAKAO;
            default:
                throw new IllegalArgumentException("Invalid registrationId: " + registrationId);
        }
    }
}
