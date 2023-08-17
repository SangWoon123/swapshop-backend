package tukorea.devhive.swapshopbackend.service.comment;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tukorea.devhive.swapshopbackend.model.dao.comment.Comment;
import tukorea.devhive.swapshopbackend.model.dao.post.Post;
import tukorea.devhive.swapshopbackend.model.dto.comment.CommentDTO;
import tukorea.devhive.swapshopbackend.repository.comment.CommentRepository;
import tukorea.devhive.swapshopbackend.repository.login.LoginRepository;
import tukorea.devhive.swapshopbackend.repository.post.PostRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final LoginRepository loginRepository;
    private final PostRepository postRepository;

    // 댓글 목록 조회
    public List<CommentDTO> comments(Long postId) {
        return commentRepository.findByPostId(postId)
                .stream()
                .map(comment -> CommentDTO.createCommentDto(comment))
                .collect(Collectors.toList());
    }

    // 댓글 생성
    @Transactional
    public CommentDTO create(Long postId, CommentDTO dto) {
        Post post = postRepository.findById(postId).orElseThrow(() ->
                new IllegalArgumentException("댓글 쓰기 실패 : 해당 게시글이 존재하지 않습니다."));

        Comment comment = Comment.createComment(dto, post);
        Comment created = commentRepository.save(comment);
        return CommentDTO.createCommentDto(created);
    }

    // 대댓글 생성
    @Transactional
    public CommentDTO createReply(Long parentCommentId, CommentDTO dto) {
        Comment parentComment = commentRepository.findById(parentCommentId).orElseThrow(() ->
                new IllegalArgumentException("대댓글 쓰기 실패 : 해당 댓글이 존재하지 않습니다."));

        Comment reply = Comment.createReply(dto, parentComment);
        reply = commentRepository.save(reply);
        return CommentDTO.createCommentDto(reply);
    }

    // 댓글 수정
    @Transactional
    public CommentDTO update(Long id, CommentDTO dto) {
        Comment target = commentRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("댓글 수정 실패 : 해당 댓글이 존재하지 않습니다."));

        target.patch(dto);
        Comment updated = commentRepository.save(target);
        return CommentDTO.createCommentDto(updated);
    }

    // 댓글 삭제
    @Transactional
    public CommentDTO delete(Long id) {
        Comment target = commentRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("댓글 삭제 실패 : 해당 댓글이 존재하지 않습니다."));

        commentRepository.delete(target);
        return CommentDTO.createCommentDto(target);
    }
}
