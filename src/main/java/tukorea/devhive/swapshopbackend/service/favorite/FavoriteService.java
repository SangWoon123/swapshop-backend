package tukorea.devhive.swapshopbackend.service.favorite;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import tukorea.devhive.swapshopbackend.model.dao.login.Login;
import tukorea.devhive.swapshopbackend.model.dao.post.Favorite;
import tukorea.devhive.swapshopbackend.model.dao.post.Post;
import tukorea.devhive.swapshopbackend.model.dto.login.LoginDTO;
import tukorea.devhive.swapshopbackend.model.dto.post.FavoriteDTO;
import tukorea.devhive.swapshopbackend.repository.favorite.FavoriteRepository;
import tukorea.devhive.swapshopbackend.repository.login.LoginRepository;
import tukorea.devhive.swapshopbackend.repository.post.PostRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FavoriteService {

    private final LoginRepository loginRepository;
    private final PostRepository postRepository;
    private final FavoriteRepository favoriteRepository;

    public ResponseEntity<?> toggleMemberFavorite(Long postId,Long loginId){
        Login user = loginRepository.findById(loginId)
                .orElseThrow(() -> new IllegalArgumentException("해당 유저가 존재하지 않습니다."));

        Post post=postRepository.findById(postId)
                .orElseThrow(()-> new IllegalArgumentException("해당 게시물이 존재하지 않습니다."));

        // 게시물 찜하기 여부 체크
        Favorite existingFavorite=user.getFavorites().stream()
                .filter(i->i.getPost().getFavorites().equals(i))
                .findFirst()
                .orElse(null);

        // existingFavorite여부로 게시물 찜하기 또는 찜하기 취소
        if(existingFavorite==null){
            Favorite favorite=new Favorite(user,post);
            favoriteRepository.save(favorite);
            return ResponseEntity.ok("찜 목록에 추가되었습니다.");
        }else{
            favoriteRepository.delete(existingFavorite);
            return ResponseEntity.ok("찜 목록에서 제거되었습니다.");

        }

    }

    public List<FavoriteDTO> findAllFavoriteByUser(LoginDTO loginDTO){
        Login user=loginRepository.findById(loginDTO.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("해당 유저가 존재하지 않습니다."));

        // 유저가 찜한 목록 리스트 찾기
        List<Favorite> findFavorite = favoriteRepository.findByLogin(user);

        // dto클래스로 반환
        return findFavorite.stream()
                .map(i->mapToDTO(i))
                .collect(Collectors.toList());
    }


    public FavoriteDTO mapToDTO(Favorite favorite){
        return FavoriteDTO.builder()
                .postId(favorite.getPost().getId())
                .build();
    }

}
