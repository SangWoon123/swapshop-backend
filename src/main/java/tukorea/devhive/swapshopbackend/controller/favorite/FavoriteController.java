package tukorea.devhive.swapshopbackend.controller.favorite;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import tukorea.devhive.swapshopbackend.controller.post.WrappedResponse;
import tukorea.devhive.swapshopbackend.model.dto.login.LoginDTO;
import tukorea.devhive.swapshopbackend.model.dto.post.FavoriteDTO;
import tukorea.devhive.swapshopbackend.service.favorite.FavoriteService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/favorite")
@Tag(name = "Favorite", description = "찜 API Document")
public class FavoriteController {

    private final FavoriteService favoriteService;

    // 게시물 찜하기, 찜 취소하기
    // 서비스코드에는 찜하기 여부에 따라 찜하거나 찜하기를 취소하는 로직이 포함되어있음
    @Operation(summary = "게시글 찜하기/취소하기", description = "게시글을 찜하고, 취소합니다.")
    @PostMapping("/{postId}")
    public ResponseEntity<?> addMemberFavorite(@PathVariable(name = "postId") Long postId, @AuthenticationPrincipal LoginDTO loginDTO){
        return favoriteService.toggleMemberFavorite(postId, loginDTO.getUserId());
    }

    // 유저가 찜한 게시물 불러오기
    @Operation(summary = "찜한 게시글 불러오기", description = "유저가 찜한 게시글을 불러옵니다.")
    @GetMapping("/my")
    public WrappedResponse<List<FavoriteDTO>> findAllFavoriteByUser(@AuthenticationPrincipal LoginDTO loginDTO){
        return new WrappedResponse<>(true,favoriteService.findAllFavoriteByUser(loginDTO),"성공");
    }






}
