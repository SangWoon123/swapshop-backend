package tukorea.devhive.swapshopbackend.controller.comment;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import tukorea.devhive.swapshopbackend.model.dto.comment.CommentDTO;
import tukorea.devhive.swapshopbackend.model.dto.login.LoginDTO;
import tukorea.devhive.swapshopbackend.service.comment.CommentService;

@RestController
@RequestMapping("/comment")
@RequiredArgsConstructor

public class CommentController {
    private final CommentService commentService;

    // 생성
    @PostMapping("/post/{id}/comment")
    public ResponseEntity commentSave(@PathVariable Long id, @RequestBody CommentDTO dto,
                                      @AuthenticationPrincipal LoginDTO userDTO) {
        return ResponseEntity.ok(commentService.commentSave(userDTO.getNickname(), id, dto));
    }

    // 개별 조회
    @GetMapping("/post/{id}/comment")
    public ResponseEntity<CommentDTO> findComment(@PathVariable Long id) {
        CommentDTO comment = commentService.findCommentById(id);
        return ResponseEntity.ok(comment);
    }

    // 수정
    @PatchMapping("/post/{postId}/comment/{id}")
    public ResponseEntity<Long> update(@PathVariable Long postId, @PathVariable Long id, @RequestBody CommentDTO dto) {
        commentService.update(postId, id, dto);
        return ResponseEntity.ok(id);
    }

    // 삭제
    @DeleteMapping("/post/{postId}/comment/{id}")
    public ResponseEntity<Long> delete(@PathVariable Long postId, @PathVariable Long id) {
        commentService.delete(postId, id);
        return ResponseEntity.ok(id);
    }
}
