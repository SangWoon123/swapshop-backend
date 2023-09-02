package tukorea.devhive.swapshopbackend.controller.post;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tukorea.devhive.swapshopbackend.model.dto.login.LoginDTO;
import tukorea.devhive.swapshopbackend.model.dto.post.PostDTO;
import tukorea.devhive.swapshopbackend.service.post.PostService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/post")
@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor
@Tag(name = "Post", description = "게시글 API Document")
public class PostController {

    private final PostService postService;

    // 생성
    @Operation(summary = "게시글 생성", description = "게시글을 생성합니다.")
    @PostMapping
    public ResponseEntity<PostDTO> create(@AuthenticationPrincipal LoginDTO userDTO,
                                          @RequestPart("post") String postJson,
                                          @RequestPart(value="image",required = false) Optional<List<MultipartFile>> image) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        //objectMapper.registerModule(new JavaTimeModule()); // LocalDateTime 직렬화
        PostDTO postDTO = objectMapper.readValue(postJson, PostDTO.class); // JSON 문자열을 PostDTO 객체로 변환
        PostDTO post = postService.create(userDTO, postDTO, image.orElse(Collections.emptyList()));
        return ResponseEntity.ok(post);
    }

    // 전체 조회
    @Operation(summary = "전체 게시글 조회", description = "전체 게시글을 조회합니다.")
    @GetMapping()
    public WrappedResponse<List<PostDTO>> findPosts(){
        return new WrappedResponse<>(true,postService.showList(),"성공");
    }

    // 내가 작성한 모든 작성글 조회
    @Operation(summary = "내가 작성한 게시글 정보 조회", description = "내가 작성한 게시글 정보를 조회합니다.")
    @GetMapping("/my")
    public ResponseEntity<List<PostDTO>> showListByLogin(@AuthenticationPrincipal LoginDTO userDTO){
        return ResponseEntity.ok(postService.showListByLogin(userDTO));
    }

    // 개별 조회
    @Operation(summary = "게시글 개별 조회", description = "게시글을 개별로 조회합니다.")
    @GetMapping("/{postId}")
    public ResponseEntity<PostDTO> findPost(HttpServletRequest request, HttpServletResponse response, @PathVariable("postId") Long postId){
        return ResponseEntity.ok(postService.getPostById(request,response,postId));
    }

    // 삭제된 리소스의 상세 정보를 함께 응답
    @Operation(summary = "게시글 삭제", description = "게시글을 삭제합니다.")
    @DeleteMapping("/{postId}")
    public ResponseEntity<PostDTO> delete(@PathVariable("postId") Long postId){
        PostDTO delete = postService.delete(postId);
        return ResponseEntity.ok(delete);
    }

    // 수정
    @Operation(summary = "게시글 수정", description = "게시글을 수정합니다.")
    @PatchMapping("/{postId}")
    public ResponseEntity<PostDTO> patchPost(@AuthenticationPrincipal LoginDTO userDTO,
                                             @PathVariable("postId") Long postId,
                                             @RequestPart(value = "post") PostDTO postDTO,
                                             @RequestPart(value="image",required = false) List<MultipartFile> image) throws IOException {
        PostDTO update=postService.update(userDTO,postId,postDTO,image);
        return ResponseEntity.ok(update);
    }

    //페이지네이션 (no-offset 방식 (무한 스크롤))
    @Operation(summary = "무한 스크롤 페이지", description = "무한 스크롤로 게시글을 조회합니다.")
    @GetMapping("/api")
    public Page<PostDTO> getPosts(@RequestParam Long lastPostId,@RequestParam int size){
        return postService.pagePost(lastPostId,size);
    }

    /**
     * 해당 클래스부터 는 게시물 검색 기능 구현 파트
     *
     * 키워드 검색
     * 가격순 정렬
     *
     * @author sangwoon kim
     * @version 1.0
     * 23/09/01
     */


    /**
     * 게시물 검색 기능구현 파트
     *
     * @param String keyword 검색하고싶은 게시물 키워드
     * @return 게시물 메인화면에 보여질 정보들을 최신순 기준으로 반환한다
     */
    // 검색기능
    @GetMapping("/search/{keyword}")
    public WrappedResponse<List<PostDTO>> searchPosts(@PathVariable("keyword") String keyword) {
        // 최신순으로 정렬하면서 페이징 처리
        //PageRequest pageRequest = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC,"createdDate"));
        return new WrappedResponse(true,postService.search(keyword),"키워드 최신순 반영");
    }

    /**
     * 게시물에 포함된 가격 정보를 기준으로 정렬하여줌
     *
     * 해당 기능은 1차 검색이 이루어진 이후에 정렬이 가능
     * 1차 keyword 검색(searchPosts)으로 반환되는 정보를 Body에 담에 요청하면 가격데이터를 기준으로 재정렬하여 반환한다.
     *
     * -> 이 방식은 데이터 양이 많아지면 서버에서 계산량이 많아지므로, 정렬을 쿼리문에서 해결해야 좋은 코드가 될 수 있다.
     *
     * @param List<PostDTO.PostMain> keywords 키워드로 검색된 게시물들
     * @return 게시물 메인화면에 보여질 정보들을 낮은 가격 기준으로 정렬하여 반환한다
     */
    @PostMapping("/search/price")
    public WrappedResponse<List<PostDTO>> sortPostsByPrice(@RequestBody List<PostDTO> keywords) {
        // 최신순으로 정렬하면서 페이징 처리
        //PageRequest pageRequest = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC,"createdDate"));
        return new WrappedResponse(true,postService.sortPostsByPrice(keywords),"가격순 정렬");
    }

    @PostMapping("/search/major")
    public WrappedResponse<List<PostDTO>> sortPostsByMajor(@RequestBody List<PostDTO> keywords) {
        // 최신순으로 정렬하면서 페이징 처리
        //PageRequest pageRequest = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC,"createdDate"));
        return new WrappedResponse(true,postService.searchAndSortByMajor(keywords),"전공별 정렬");
    }

    @PostMapping("/search/title")
    public WrappedResponse<List<PostDTO>> sortPostsByTitle(@RequestBody List<PostDTO> keywords) {
        // 최신순으로 정렬하면서 페이징 처리
        //PageRequest pageRequest = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC,"createdDate"));
        return new WrappedResponse(true,postService.searchAndSortByTitle(keywords),"가나다렬 정렬");
    }

}
