package tukorea.devhive.swapshopbackend.service.post;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import tukorea.devhive.swapshopbackend.bean.S3Uploader;
import tukorea.devhive.swapshopbackend.model.Enum.login.AuthenticationType;
import tukorea.devhive.swapshopbackend.model.category.Category;
import tukorea.devhive.swapshopbackend.model.dao.post.Image;
import tukorea.devhive.swapshopbackend.model.dao.post.Post;
import tukorea.devhive.swapshopbackend.model.dao.login.Login;
import tukorea.devhive.swapshopbackend.model.dto.CategoryDTO;
import tukorea.devhive.swapshopbackend.model.dto.login.LoginDTO;
import tukorea.devhive.swapshopbackend.model.dto.post.ImageDTO;
import tukorea.devhive.swapshopbackend.model.dto.post.PostDTO;
import tukorea.devhive.swapshopbackend.repository.login.LoginRepository;
import tukorea.devhive.swapshopbackend.repository.post.PostRepository;
import tukorea.devhive.swapshopbackend.service.category.CategoryService;

import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {

    private final LoginRepository loginRepository;
    private final PostRepository postRepository;
    private final CategoryService categoryService;
    private final S3Uploader s3Uploader;

    public PostDTO create(LoginDTO loginDTO, PostDTO postDTO,List<MultipartFile> images) throws IOException {

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

        categoryService.search(postDTO.getCategory(),post);


        //if(!images.isEmpty()) -> null값이면 오류발생
        if (images!=null) {
            // 이미지 엔티티 생성 및 추가
            List<Image> imageEntities = new ArrayList<>();

            for (MultipartFile file : images) {
                String uploadUrl = s3Uploader.upload(file, "images");
                Image image = Image.builder().filePath(uploadUrl).build();
                imageEntities.add(image); // 이미지 엔티티를 리스트에 추가
            }
            post.setImages(imageEntities); // 이미지 엔티티 리스트를 게시물에 설정
        }


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
                .images(postDTO.getImages())
                .category(postDTO.getCategory())
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
                    mapToDto(post)
                ).collect(Collectors.toList());
    }

    // 모든 게시물 조회
    public List<PostDTO> showList() {
        List<Post> allPosts = postRepository.findAll();
        // !! 추후 게시물 작성이 모두 완성되었을때 따로 분리해서 코드 작성 필요 !!
        return allPosts.stream()
                .map(post ->
                        mapToDto(post)
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
        // S3에서 이미지 삭제
        List<String> imageUrls = post.getImages().stream()
                .map(Image::getFilePath)
                .collect(Collectors.toList());
        for (String imageUrl : imageUrls) {
            s3Uploader.deleteImage(imageUrl);
        }

        // !! 추후 게시물 작성이 모두 완성되었을때 따로 분리해서 코드 작성 필요 !!
        PostDTO postDTO = mapToDto(post);
        postRepository.delete(post);
        return postDTO;

    }

    public PostDTO update(LoginDTO userDTO, Long postId,PostDTO postDTO,List<MultipartFile> images) throws IOException {
        // 유저가 작성한 게시물인지 확인하고 맞다면 삭제
        Post post=postRepository.findById(postId)
                .orElseThrow(()->new IllegalArgumentException("해당 게시물이 존재하지 않습니다."));

        Long postById = post.getLogin().getId();
        Long userId = userDTO.getUserId();

        if(!userId.equals(postById)){
            new AccessDeniedException("해당 게시물을 수정할 권한이 없습니다.");
        }

        // S3에서 이미지 삭제
        List<String> imageUrls = post.getImages().stream()
                .map(Image::getFilePath)
                .collect(Collectors.toList());
        for (String imageUrl : imageUrls) {
            s3Uploader.deleteImage(imageUrl);
        }

        // 이미지 업데이트
        List<Image> updatedImages = new ArrayList<>();
        if (images != null && !images.isEmpty()) {
            for (MultipartFile file : images) {
                String uploadUrl = s3Uploader.upload(file, "images");
                Image image = Image.builder().filePath(uploadUrl).build();
                updatedImages.add(image);
            }
        }

        // 카테고리 업데이트
        Category category = categoryService.mapCategoryToEntity(postDTO.getCategory());
        post.setCategory(category);

        //변경감지를 통한 업데이트 적용
        post.update(postDTO.getTitle(), postDTO.getContent(), postDTO.getPrice(), postDTO.getLocation(), postDTO.getDesiredTime()
                ,postDTO.getStatus(), postDTO.getViews(),updatedImages);

        // 게시물 업데이트
        Post updatedPost = postRepository.save(post);

        // 수정된 게시물 정보를 PostDTO로 변환하여 반환
        return mapToDto(updatedPost);
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
                .images(mapImageToDto(post.getImages()))
                .category(mapCategoryToDto(post.getCategory()))
                .build();
    }

    private List<ImageDTO> mapImageToDto(List<Image> images) {
        List<ImageDTO> imageDTOList = new ArrayList<>();
        for (Image image : images) {
            ImageDTO imageDTO = ImageDTO.builder()
                    .filePath(image.getFilePath())
                    .id(image.getId())
                    .build();
            imageDTOList.add(imageDTO);
        }
        return imageDTOList;
    }

    private CategoryDTO mapCategoryToDto(Category category) {
        return CategoryDTO.builder()
                .name(category.getName())
                .professor(category.getProfessor())
                .major(category.getMajor())
                .code(category.getCode())
                .build();
    }



}
