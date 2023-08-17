package tukorea.devhive.swapshopbackend.controller.login;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import tukorea.devhive.swapshopbackend.model.dto.login.LoginDTO;
import tukorea.devhive.swapshopbackend.service.login.UserInfoService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user") // url 추후 결정
@CrossOrigin(origins = "http://localhost:3000")
public class UserInfoController {

    private final UserInfoService userInfoService;
    @PostMapping("/new")
    public ResponseEntity<Void> AddUserInfo(@RequestBody LoginDTO loginDTO){
        userInfoService.addInfo(loginDTO);
        return ResponseEntity.ok().build();
    }

    @GetMapping()
    public LoginDTO userId(@AuthenticationPrincipal LoginDTO loginDTO){
        return LoginDTO.builder().userId(loginDTO.getUserId()).build();
    }

}
