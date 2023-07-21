package tukorea.devhive.swapshopbackend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tukorea.devhive.swapshopbackend.model.Enum.login.AuthenticationType;
import tukorea.devhive.swapshopbackend.model.dao.login.Login;

@RestController
@RequestMapping("/auth")
public class TestController {

    @GetMapping("/test")
    public ResponseEntity<String> testEndpoint(Authentication authentication) {
        if (authentication.getPrincipal() instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String username = userDetails.getUsername();

            // 로그인한 사용자의 정보를 확인하고자 하는 로직 수행
            // ...

            // 응답 생성
            String response = "User: " + username + " is authenticated";
            return ResponseEntity.ok(response);
        } else {
            // OAuth2 사용자의 경우 UserDetails가 아닌 OAuth2User이므로 다른 처리 방식 필요
            // 예를 들어, OAuth2User에서 이메일 정보를 가져와서 처리하는 등의 방법
            String email = authentication.getPrincipal().toString();


            // 로그인한 사용자의 정보를 확인하고자 하는 로직 수행
            // ...

            // 응답 생성
            String response = "User with email: " + email + " is authenticated";
            return ResponseEntity.ok(response);
        }
    }

    @GetMapping("/good")
    public String test3(){
        Login login= Login.builder()
                .authType(AuthenticationType.GOOGLE)
                .build();

        if(login.getAuthType().toString().equals("GOOGLE")){
            return "동일";
        }
        return "낫동일";
    }

}
