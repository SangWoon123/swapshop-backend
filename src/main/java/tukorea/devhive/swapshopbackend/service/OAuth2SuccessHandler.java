package tukorea.devhive.swapshopbackend.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;
import tukorea.devhive.swapshopbackend.model.dto.Token;
import tukorea.devhive.swapshopbackend.model.dto.UserDTO;
import tukorea.devhive.swapshopbackend.model.dto.UserRequestMapper;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
@Component
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private final TokenService tokenService;
    private final UserRequestMapper userRequestMapper;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        OAuth2User oAuth2User=(OAuth2User) authentication.getPrincipal();

        UserDTO userDto =userRequestMapper.toDto(oAuth2User);

        log.info("Principal에서 꺼낸 OAuth2User = {}", oAuth2User); // 다음 로그는 디버깅 확인을위한 로그로 운영시에 주석처리 필수

        // 최초 로그인이라면 회원가입 처리를 한다.
        String targetUrl;
        log.info("토큰 발행 시작");

        System.out.println("userDto"+userDto.getEmail());

        Token token = tokenService.generateToken(userDto.getEmail(),"USER");
        String accessToken=token.getToken();
        log.info("{}", token);


        targetUrl = UriComponentsBuilder.fromUriString("http://localhost:3000/user/login")  //프론트 리다이렉트 부분 추후 설정
                .queryParam("token", token)
                .build().toUriString();

        getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }
}
