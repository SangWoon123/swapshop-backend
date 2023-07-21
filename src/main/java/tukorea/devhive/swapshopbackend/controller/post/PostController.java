package tukorea.devhive.swapshopbackend.controller.post;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import tukorea.devhive.swapshopbackend.model.dao.post.Post;
import tukorea.devhive.swapshopbackend.model.dto.login.LoginDTO;
import tukorea.devhive.swapshopbackend.model.dto.post.PostDTO;
import tukorea.devhive.swapshopbackend.service.post.PostService;

import java.util.List;

@RestController
@RequestMapping("/post")
@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    // 생성
    @PostMapping()
    public ResponseEntity<PostDTO> create(@AuthenticationPrincipal LoginDTO userDTO,@RequestBody PostDTO postDTO){
        // 글 작성
        PostDTO post = postService.create(userDTO, postDTO);
        return ResponseEntity.ok(post);
    }

    // 전체 조회
    @GetMapping()
    public ResponseEntity<List<PostDTO>> findPosts(){
        return ResponseEntity.ok(postService.showList());
    }

    // 내가 작성한 모든 작성글 조회
    @GetMapping("/my")
    public ResponseEntity<List<PostDTO>> showListByLogin(@AuthenticationPrincipal LoginDTO userDTO){
        return ResponseEntity.ok(postService.showListByLogin(userDTO));
    }

    // 개별 조회
    @GetMapping("/{postId}")
    public ResponseEntity<PostDTO> findPost(@PathVariable("postId") Long postId){
        return ResponseEntity.ok(postService.getPostById(postId));
    }

    // 삭제된 리소스의 상세 정보를 함께 응답
    @DeleteMapping("/{postId}")
    public ResponseEntity<PostDTO> delete(@PathVariable("postId") Long postId){
        PostDTO delete = postService.delete(postId);
        return ResponseEntity.ok(delete);
    }

    // 수정
    @PatchMapping("/{postId}")
    public ResponseEntity<PostDTO> patchPost(@AuthenticationPrincipal LoginDTO userDTO, @PathVariable("postId") Long postId,PostDTO postDTO){
        PostDTO update=postService.update(userDTO,postId,postDTO);
        return ResponseEntity.ok(update);
    }

}
