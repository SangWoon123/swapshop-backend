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

@RestController
@RequiredArgsConstructor
@RequestMapping("/favorite")
public class FavoriteController {

    private final FavoriteService favoriteService;

    // 게시물 찜하기, 찜 취소하기
    // 서비스코드에는 찜하기 여부에 따라 찜하거나 찜하기를 취소하는 로직이 포함되어있음
    @PostMapping("/{postId}")
    public ResponseEntity<?> addMemberFavorite(@PathVariable(name = "postId") Long postId, @AuthenticationPrincipal LoginDTO loginDTO){
        return favoriteService.toggleMemberFavorite(postId, loginDTO.getUserId());
    }

    // 유저가 찜한 게시물 불러오기
    @GetMapping("/my")
    public WrappedResponse<List<FavoriteDTO>> findAllFavoriteByUser(@AuthenticationPrincipal LoginDTO loginDTO){
        return new WrappedResponse<>(true,favoriteService.findAllFavoriteByUser(loginDTO),"성공");
    }






}
