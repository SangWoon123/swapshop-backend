package tukorea.devhive.swapshopbackend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import tukorea.devhive.swapshopbackend.model.dto.UserDto;

@RestController
public class HelloContller {

    @GetMapping("/oauth2/code/kakao")
    public String test(@RequestParam String code){
        return code;
    }

    @GetMapping("/oauth2/kakao")
    public String test2(){
        return "회원가입을 하세요.";
    }

    @GetMapping("/profile")
    public ResponseEntity<UserDto> getProfile(@AuthenticationPrincipal UserDto user) {
        // 현재 인증된 사용자의 정보(user)를 활용하여 프로필 정보 반환
        return ResponseEntity.ok(user);
    }


}
