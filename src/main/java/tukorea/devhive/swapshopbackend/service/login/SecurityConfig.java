package tukorea.devhive.swapshopbackend.service.login;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import tukorea.devhive.swapshopbackend.model.Enum.login.UserRole;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CustomOAuth2UserService oAuth2UserService;
    private final OAuth2SuccessHandler successHandler;
    private final JwtAuthFilter jwtAuthFilter;

    @Override
    protected void configure(HttpSecurity http) throws Exception{
        http.cors().configurationSource(request -> new CorsConfiguration().applyPermitDefaultValues());
        http.cors().disable() // CORS 비활성화
                .csrf().disable() // CSRF 비활성화
                .httpBasic().disable() // 기본 인증 비활성화
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.OPTIONS).permitAll()
                .antMatchers("/","/auth/**","/oauth2/**","/post/**","/categories","/**").permitAll()
                .antMatchers("/security-login/admin/**").hasAuthority(UserRole.ADMIN.name())  // ADMIN인 유저만 진입가능
                .anyRequest()
                .authenticated()
                .and()
                .oauth2Login()
                .redirectionEndpoint()
                .baseUri("/oauth2/callback/*")
                .and()
                .authorizationEndpoint() // 원하는 경로로 바꾸는 설정 1
                .baseUri("/user/new")// 원하는 경로를 설정 2
                .and()
               // .loginPage("/user/login") //.loginPage()는 인증되지 않은 사용자가 접근했을때 리다이렉트되는 페이지를 설정
                .userInfoEndpoint()
                .userService(oAuth2UserService)
                .and()
                .successHandler(successHandler);

        http.addFilterAfter(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
    }






}
