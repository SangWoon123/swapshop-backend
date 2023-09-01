package tukorea.devhive.swapshopbackend.controller.favorite;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import tukorea.devhive.swapshopbackend.controller.post.WrappedResponse;
import tukorea.devhive.swapshopbackend.model.dao.post.Favorite;
import tukorea.devhive.swapshopbackend.model.dto.login.LoginDTO;
import tukorea.devhive.swapshopbackend.model.dto.post.FavoriteDTO;
import tukorea.devhive.swapshopbackend.service.favorite.FavoriteService;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/favorite")
@CrossOrigin(origins = "http://localhost:3000")
public class FavoriteController {

    private final FavoriteService favoriteService;

    // 게시물 찜하기, 찜 취소하기
    // 서비스코드에는 찜하기 여부에 따라 찜하거나 찜하기를 취소하는 로직이 포함되어있음

    // ver 1.0 에서 API 가 POST인데 Body에 아무것도 없이 전달하는 바람에 프론트측에서 받으면 오류가 발생하는 상황이었음
//    @PostMapping("/{postId}")
//    public ResponseEntity<?> addMemberFavorite(@PathVariable(name = "postId") Long postId, @AuthenticationPrincipal LoginDTO loginDTO){
//        return favoriteService.toggleMemberFavorite(postId, loginDTO.getUserId());
//    }

    // RequestBody Map<String,Long> 으로 데이터 전달 받음 (아래 예시)
    /* {
        "postId":1
        }
    */
    @PostMapping()
    public ResponseEntity<?> addMemberFavorite(@RequestBody Map<String,Long> postId, @AuthenticationPrincipal LoginDTO loginDTO){
        return favoriteService.toggleMemberFavorite(postId.get("postId"), loginDTO.getUserId());
    }

    // 유저가 찜한 게시물 불러오기
    @GetMapping("/my")
    public WrappedResponse<List<FavoriteDTO>> findAllFavoriteByUser(@AuthenticationPrincipal LoginDTO loginDTO){
        return new WrappedResponse<>(true,favoriteService.findAllFavoriteByUser(loginDTO),"성공");
    }






}
