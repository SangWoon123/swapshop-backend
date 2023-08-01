package tukorea.devhive.swapshopbackend.controller.comment;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import tukorea.devhive.swapshopbackend.model.dao.post.Post;
import tukorea.devhive.swapshopbackend.model.dto.comment.CommentDTO;
import tukorea.devhive.swapshopbackend.model.dto.login.LoginDTO;
import tukorea.devhive.swapshopbackend.repository.post.PostRepository;
import tukorea.devhive.swapshopbackend.service.comment.CommentService;

@RestController
@RequestMapping("/post")
@RequiredArgsConstructor

public class CommentController {
    private final CommentService commentService;
    private final PostRepository postRepository;

//    // 생성
//    @PostMapping("/{postId}/comment")
//    public ResponseEntity commentSave(@PathVariable Long postId, @RequestBody CommentDTO dto,
//                                      @AuthenticationPrincipal LoginDTO userDTO) {
//        Long categoryId = dto.getCategory().getId();
//        return ResponseEntity.ok(commentService.commentSave(userDTO.getNickname(), postId, dto));
//    }

    // 생성
    @PostMapping("/{postId}/comment")
    public ResponseEntity commentSave(@PathVariable Long postId, @RequestBody CommentDTO dto,
                                      @AuthenticationPrincipal LoginDTO userDTO) {
        Post post = postRepository.findById(postId).orElseThrow(() ->
                new IllegalArgumentException("댓글 쓰기 실패 : 해당 게시글이 존재하지 않습니다." + postId));

        Long commentId = commentService.commentSave(userDTO.getNickname(), postId, dto);

        // 생성된 댓글의 ID를 응답으로 보내줍니다.
        return ResponseEntity.ok(commentId);
    }

    // 개별 조회
    @GetMapping("/{postId}/comment/{id}")
    public ResponseEntity<CommentDTO> findComment(@PathVariable Long postId, @PathVariable Long id) {
        CommentDTO comment = commentService.findCommentById(id);
        return ResponseEntity.ok(comment);
    }

    // 수정
    @PatchMapping("/{postId}/comment/{id}")
    public ResponseEntity<Long> update(@PathVariable Long postId, @PathVariable Long id, @RequestBody CommentDTO dto) {
        commentService.update(postId, id, dto);
        return ResponseEntity.ok(id);
    }

    // 삭제
    @DeleteMapping("/{postId}/comment/{id}")
    public ResponseEntity<Long> delete(@PathVariable Long postId, @PathVariable Long id) {
        commentService.delete(postId, id);
        return ResponseEntity.ok(id);
    }
}
