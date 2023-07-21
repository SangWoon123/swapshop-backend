package tukorea.devhive.swapshopbackend.service.post;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tukorea.devhive.swapshopbackend.model.Enum.login.AuthenticationType;
import tukorea.devhive.swapshopbackend.model.dao.post.Post;
import tukorea.devhive.swapshopbackend.model.dao.login.Login;
import tukorea.devhive.swapshopbackend.model.dto.login.LoginDTO;
import tukorea.devhive.swapshopbackend.model.dto.post.PostDTO;
import tukorea.devhive.swapshopbackend.repository.login.LoginRepository;
import tukorea.devhive.swapshopbackend.repository.post.PostRepository;

import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {

    private final LoginRepository loginRepository;
    private final PostRepository postRepository;

    public PostDTO create(LoginDTO loginDTO, PostDTO postDTO){

        String email=loginDTO.getEmail();
        AuthenticationType authenticationType=loginDTO.getAuthenticationType();

        Login login=loginRepository.findByEmailAndAuthType(email,authenticationType)
                .orElseThrow(()-> new IllegalArgumentException("유저가 존재하지 않습니다."));

        // !! 추후 게시물 작성이 모두 완성되었을때 따로 분리해서 코드 작성 필요 !!
        Post post=Post.builder()
                .login(login)
                .title(postDTO.getTitle())
                .content(postDTO.getContent())
                .price(postDTO.getPrice())
                .location(postDTO.getLocation())
                .desiredTime(postDTO.getDesiredTime())
                .status(postDTO.getStatus())
                .views(postDTO.getViews())
                .build();

        postRepository.save(post);

        return PostDTO.builder()
                .id(post.getId())
                .login(post.getLogin())
                .title(post.getTitle())
                .content(post.getContent())
                .price(post.getPrice())
                .location(post.getLocation())
                .desiredTime(post.getDesiredTime())
                .status(post.getStatus())
                .views(post.getViews())
                .build();

    }

    // 개별 회원 작성글 조회
    public List<PostDTO> showListByLogin(LoginDTO loginDTO){
        String email=loginDTO.getEmail();
        AuthenticationType authenticationType=loginDTO.getAuthenticationType();

        Login login=loginRepository.findByEmailAndAuthType(email,authenticationType)
                .orElseThrow(()-> new IllegalArgumentException("유저가 존재하지 않습니다."));

        List<Post> posts = postRepository.findByLogin(login);

        // !! 추후 게시물 작성이 모두 완성되었을때 따로 분리해서 코드 작성 필요 !!
        return posts.stream()
                .map(post ->
                    PostDTO.builder()
                            .id(post.getId())
                            .title(post.getTitle())
                            .content(post.getContent())
                            .build()
                ).collect(Collectors.toList());
    }

    // 모든 게시물 조회
    public List<PostDTO> showList() {
        List<Post> allPosts = postRepository.findAll();

        // !! 추후 게시물 작성이 모두 완성되었을때 따로 분리해서 코드 작성 필요 !!
        return allPosts.stream()
                .map(post ->
                        PostDTO.builder()
                                .id(post.getId())
                                .title(post.getTitle())
                                .content(post.getContent())
                                .build()
                ).collect(Collectors.toList());
    }


    // 개별 조회
    public PostDTO getPostById(Long postId) {
        Post post=postRepository.findById(postId)
                .orElseThrow(()-> new IllegalArgumentException("해당 게시물이 존재하지 않습니다."));

        // !! 추후 게시물 작성이 모두 완성되었을때 따로 분리해서 코드 작성 필요 !!
        return mapToDto(post);
    }

    public PostDTO delete(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(()->new IllegalArgumentException("해당 게시물이 존재하지 않습니다."));

        // !! 추후 게시물 작성이 모두 완성되었을때 따로 분리해서 코드 작성 필요 !!
        PostDTO postDTO = PostDTO.builder()
                .title(post.getTitle())
                .content(post.getContent())
                .build();

        postRepository.delete(post);

        return postDTO;

    }

    public PostDTO update(LoginDTO userDTO, Long postId,PostDTO postDTO) {
        // 유저가 작성한 게시물인지 확인하고 맞다면 삭제
        Post post=postRepository.findById(postId)
                .orElseThrow(()->new IllegalArgumentException("해당 게시물이 존재하지 않습니다."));

        Long postById = post.getLogin().getId();
        Long userId = userDTO.getUserId();

        if(!userId.equals(postById)){
            new AccessDeniedException("해당 게시물을 수정할 권한이 없습니다.");
        }

        //변경감지를 통한 업데이트 적용
        post.update(postDTO.getTitle(), postDTO.getContent(), postDTO.getPrice(), postDTO.getLocation(), postDTO.getDesiredTime()
        ,postDTO.getStatus(), postDTO.getViews(),postDTO.getCategories());


        // 수정된 게시물 정보를 PostDTO로 변환하여 반환
        return mapToDto(post);
    }

    public PostDTO mapToDto(Post post){
        return PostDTO.builder()
                .id(post.getId())
                //.login(post.getLogin())
                .title(post.getTitle())
                .content(post.getContent())
                .price(post.getPrice())
                .location(post.getLocation())
                .desiredTime(post.getDesiredTime())
                .views(post.getViews())
                .status(post.getStatus())
                .build();
    }
}
