package tukorea.devhive.swapshopbackend.service.login;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import tukorea.devhive.swapshopbackend.model.Enum.login.AuthenticationType;
import tukorea.devhive.swapshopbackend.model.dao.login.Login;
import tukorea.devhive.swapshopbackend.model.dto.login.LoginDTO;
import tukorea.devhive.swapshopbackend.repository.login.LoginRepository;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtAuthFilter extends OncePerRequestFilter {

    private final TokenService tokenService;
    private final LoginRepository loginRepository;
    @Override
    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException{
        String token = parseBearerToken(request); // Bearer 을 제외한 토큰


        if (token != null && tokenService.verifyToken(token)) {

            // userId(email), authType(authType) 을 token을 기준으로 가져옴
            String userId = tokenService.getUid(token);
            String authType=tokenService.getAuthType(token);

            // userId(email) 을 기준으로 유저정보 검색해서 가져오기
            Optional<Login> byEmail = loginRepository.findByEmailAndAuthType(userId, AuthenticationType.valueOf(authType));
            LoginDTO login = byEmail.map(LoginDTO::mapToDto).orElse(null);

            Authentication authentication = getAuthentication(login);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        chain.doFilter(request, response);
    }

    public Authentication getAuthentication(LoginDTO loginDTO) {
        return new UsernamePasswordAuthenticationToken(loginDTO, null,
                AuthorityUtils.NO_AUTHORITIES);
    }
    private String parseBearerToken(HttpServletRequest request){
        String barerToken=request.getHeader(HttpHeaders.AUTHORIZATION);

        if (StringUtils.hasText(barerToken) && barerToken.startsWith("Bearer ")) {
            return barerToken.substring(7);
        }
        return null;
    }


}