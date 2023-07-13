package tukorea.devhive.swapshopbackend.service;


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
import tukorea.devhive.swapshopbackend.repository.LoginRepository;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtAuthFilter extends OncePerRequestFilter {

    private final TokenService tokenService;
    @Override
    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException{
        String token = parseBearerToken(request); // Bearer 을 제외한 토큰

        if (token != null && tokenService.verifyToken(token)) {
            String userId = tokenService.getUid(token);
            Authentication authentication = getAuthentication(userId);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        chain.doFilter(request, response);
    }

    public Authentication getAuthentication(String userId) {
        return new UsernamePasswordAuthenticationToken(userId, null,
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