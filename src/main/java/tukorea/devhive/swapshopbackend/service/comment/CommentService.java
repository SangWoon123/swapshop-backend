package tukorea.devhive.swapshopbackend.service.comment;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tukorea.devhive.swapshopbackend.model.dao.comment.Comment;
import tukorea.devhive.swapshopbackend.model.dao.login.Login;
import tukorea.devhive.swapshopbackend.model.dao.post.Post;
import tukorea.devhive.swapshopbackend.model.dto.comment.CommentDTO;
import tukorea.devhive.swapshopbackend.repository.comment.CommentRepository;
import tukorea.devhive.swapshopbackend.repository.login.LoginRepository;
import tukorea.devhive.swapshopbackend.repository.post.PostRepository;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final LoginRepository loginRepository;
    private final PostRepository postRepository;

    public CommentDTO findCommentById(Long id) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 댓글이 존재하지 않습니다. id = " + id));

        CommentDTO commentDTO = new CommentDTO();
        // Comment 엔티티에서 필요한 정보를 CommentDTO로 복사하는 작업 수행
        commentDTO.setId(comment.getId());
        commentDTO.setContent(comment.getContent());
        // 다른 필요한 정보들도 복사

        return commentDTO;
    }

    // 생성
    @Transactional
    public Long commentSave(String nickname, Long id, CommentDTO dto) {
        Login login = loginRepository.findByNickname(nickname);
        Post post = postRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("댓글 쓰기 실패 : 해당 게시글이 존재하지 않습니다." + id));

        dto.setLogin(login);
        dto.setPost(post);

        Comment comment = dto.toEntity();
        commentRepository.save(comment);

        return dto.getId();
    }

    // 수정
    @Transactional
    public void update(Long postId, Long id, CommentDTO dto) {
        Comment comment = (Comment) commentRepository.findByPostIdAndId(postId, id)
                .orElseThrow(() -> new IllegalArgumentException("해당 댓글이 존재하지 않습니다. " + id));

        comment.update(dto.getContent());
    }

    // 삭제
    @Transactional
    public void delete(Long postId, Long id) {
        Comment comment = (Comment) commentRepository.findByPostIdAndId(postId, id)
                .orElseThrow(() -> new IllegalArgumentException("해당 댓글이 존재하지 않습니다. id = " + id));

        commentRepository.delete(comment);
    }
}
