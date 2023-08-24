package tukorea.devhive.swapshopbackend.controller.comment;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tukorea.devhive.swapshopbackend.model.dto.comment.CommentDTO;
import tukorea.devhive.swapshopbackend.service.comment.CommentService;

import java.util.List;

@RestController
@RequestMapping("/post")
@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor

public class CommentController {
    private final CommentService commentService;

    // 댓글 목록 조회
    @GetMapping("/{postId}/comment")
    public ResponseEntity<List<CommentDTO>> comments(@PathVariable Long postId) {

        List<CommentDTO> dtos = commentService.comments(postId);

        return ResponseEntity.status(HttpStatus.OK).body(dtos);
    }

    // 생성
    @PostMapping("/{postId}/comment")
    public ResponseEntity<CommentDTO> create(@PathVariable Long postId, @RequestBody CommentDTO dto) {

        CommentDTO createDto = commentService.create(postId, dto);

        return ResponseEntity.status(HttpStatus.OK).body(createDto);
    }

    // 대댓글 생성
    @PostMapping("/{postId}/comment/{parentId}")
    public ResponseEntity<CommentDTO> createReply(
            @PathVariable Long postId,
            @PathVariable Long parentId,
            @RequestBody CommentDTO dto) {

        CommentDTO createDto = commentService.createReply(postId, parentId, dto);

        return ResponseEntity.status(HttpStatus.OK).body(createDto);
    }


    // 수정
    @PatchMapping("/{postId}/comment/{id}")
    public ResponseEntity<CommentDTO> update(@PathVariable Long id, @RequestBody CommentDTO dto) {

        CommentDTO updateDto = commentService.update(id, dto);

        return ResponseEntity.status(HttpStatus.OK).body(updateDto);
    }

    // 삭제
    @DeleteMapping("/{postId}/comment/{id}")
    public ResponseEntity<CommentDTO> delete(@PathVariable("id") Long id) {

        CommentDTO deleteDto = commentService.delete(id);

        return ResponseEntity.ok(deleteDto);
//        return ResponseEntity.status(HttpStatus.OK).body(deleteDto)
    }
}

