package tukorea.devhive.swapshopbackend.controller.login;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "User", description = "유저 API Document")
public class UserInfoController {

    private final UserInfoService userInfoService;
    
    @Operation(summary = "유저 생성", description = "유저를 생성합니다.")
    @PostMapping("/new")
    public ResponseEntity<Void> AddUserInfo(@RequestBody LoginDTO loginDTO){
        userInfoService.addInfo(loginDTO);
        return ResponseEntity.ok().build();
    }
    
    @Operation(summary = "유저 조회", description = "유저 목록을 조회합니다.")
    @GetMapping()
    public LoginDTO userId(@AuthenticationPrincipal LoginDTO loginDTO){
        return LoginDTO.builder().userId(loginDTO.getUserId()).build();
    }

}
