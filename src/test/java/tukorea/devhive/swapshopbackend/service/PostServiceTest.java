package tukorea.devhive.swapshopbackend.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import tukorea.devhive.swapshopbackend.model.Enum.login.AuthenticationType;
import tukorea.devhive.swapshopbackend.model.Enum.post.TradeStatus;
import tukorea.devhive.swapshopbackend.model.category.Category;
import tukorea.devhive.swapshopbackend.model.category.PostCategory;
import tukorea.devhive.swapshopbackend.model.dao.post.Post;
import tukorea.devhive.swapshopbackend.model.dao.login.Login;
import tukorea.devhive.swapshopbackend.model.dto.PostCategoryDTO;
import tukorea.devhive.swapshopbackend.model.dto.login.LoginDTO;
import tukorea.devhive.swapshopbackend.model.dto.post.PostDTO;
import tukorea.devhive.swapshopbackend.repository.category.CategoryRepository;
import tukorea.devhive.swapshopbackend.repository.login.LoginRepository;
import tukorea.devhive.swapshopbackend.repository.post.PostRepository;
import tukorea.devhive.swapshopbackend.service.post.PostService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@Transactional
class PostServiceTest {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private LoginRepository loginRepository;

    @Autowired
    private PostService postService;

    @Autowired
    private CategoryRepository categoryRepository;


    // 생성 테스트
    @Test
    public void createTest(){
        // Given
        Login login = Login.builder()
                .email("test1@kakao.com")
                .nickname("tester")
                .authType(AuthenticationType.KAKAO)
                .build();


        PostDTO postDTO = new PostDTO();
        postDTO.setTitle("Test Title");
        postDTO.setContent("Test Content");

        Login save = loginRepository.save(login);

        //when
        //PostDTO post = postService.create(LoginDTO.mapToDto(login), postDTO);


        //then
        //Assertions.assertEquals(save.getId(),post.getLogin().getId(),"다른 게시물입니다.");

    }

    //개별 조회
    @Test
    void getPostById() {
        //given
        Login login = Login.builder()
                .email("test1@kakao.com")
                .nickname("tester")
                .authType(AuthenticationType.KAKAO)
                .build();

        Post post= Post.builder()
                .login(login)
                .title("제목 입니다.")
                .content("내용 테스트")
                .build();

        Post save = postRepository.save(post);

        //when
        PostDTO postById = postService.getPostById(save.getId());

        //then
        Assertions.assertEquals(save.getTitle(),postById.getTitle());
        Assertions.assertEquals(save.getId(),postById.getId());
        Assertions.assertEquals(save.getContent(),postById.getContent());
    }

    // 로그인한 유저의 작성글 조회
    @Test
    void showListByLogin() {
        //given
        Login login = Login.builder()
                .email("test1@kakao.com")
                .nickname("tester")
                .authType(AuthenticationType.KAKAO)
                .build();

        loginRepository.save(login);

        Post post1= Post.builder()
                .login(login)
                .title("제목 입니다.")
                .content("내용 테스트")
                .build();

        Post post2= Post.builder()
                .login(login)
                .title("제목 입니다.2")
                .content("내용 테스트2")
                .build();

        postRepository.save(post1);
        postRepository.save(post2);

        //다른유저
        Login login2 = Login.builder()
                .email("test2@kakao.com")
                .nickname("tester2")
                .authType(AuthenticationType.KAKAO)
                .build();

        loginRepository.save(login2);

        Post post3= Post.builder()
                .login(login2)
                .title("제목 입니다.3")
                .content("내용 테스트3")
                .build();

        postRepository.save(post3);


        //when
        List<Post> byLogin = postRepository.findByLogin(login);


        //then
        byLogin.stream()
                .map(Post::getContent)
                .forEach(System.out::println);

        Assertions.assertEquals(2,byLogin.size());
    }

    // 모두 조회
    @Test
    void showList() {
        //given
        Login login = Login.builder()
                .email("test1@kakao.com")
                .nickname("tester")
                .authType(AuthenticationType.KAKAO)
                .build();

        loginRepository.save(login);

        Post post1= Post.builder()
                .login(login)
                .title("제목 입니다.")
                .content("내용 테스트")
                .build();

        Post post2= Post.builder()
                .login(login)
                .title("제목 입니다.2")
                .content("내용 테스트2")
                .build();

        postRepository.save(post1);
        postRepository.save(post2);

        //다른유저
        Login login2 = Login.builder()
                .email("test2@kakao.com")
                .nickname("tester2")
                .authType(AuthenticationType.KAKAO)
                .build();

        loginRepository.save(login2);

        Post post3= Post.builder()
                .login(login2)
                .title("제목 입니다.3")
                .content("내용 테스트3")
                .build();

        postRepository.save(post3);

        //when
        List<Post> all = postRepository.findAll();

        //then
        Assertions.assertEquals(3,all.size());
    }

    @Test
    void delete() {
        //given
        Login login = Login.builder()
                .email("test1@kakao.com")
                .nickname("tester")
                .authType(AuthenticationType.KAKAO)
                .build();

        Post post1= Post.builder()
                .login(login)
                .title("제목 입니다.")
                .content("내용 테스트")
                .build();

        loginRepository.save(login);
        Post save = postRepository.save(post1);

        //when
        postService.delete(save.getId());

        //then
        Assertions.assertEquals(0,postRepository.findByLogin(login).size());
    }

//    @Test
//    void update() throws InterruptedException {
//        //given
//        Login login = Login.builder()
//                .email("test1@kakao.com")
//                .nickname("tester")
//                .authType(AuthenticationType.KAKAO)
//                .build();
//
//        Post post= Post.builder()
//                .login(login)
//                .title("제목 입니다.")
//                .content("내용 테스트")
//                .build();
//
//        loginRepository.save(login);
//        Post save = postRepository.save(post);
//
//        //when
//        PostDTO updatePost= PostDTO.builder()
//                .id(post.getId())
//                .title("제목이 성공적으로 변경완료")
//                .content("내용이 성공적으로 변경완료")
//                .build();
//
//
//        LoginDTO loginDTO = LoginDTO.mapToDto(login);
//        PostDTO updatedPost = postService.update(loginDTO, save.getId(), updatePost);
//
//        Post test=postRepository.findById(post.getId())
//                .orElseThrow(()->new IllegalArgumentException("zzz"));
//
//        Assertions.assertEquals(updatedPost.getId(),test.getId());
//
//    }

    @Test
    public void categoryTest(){
        // 카테고리1 테스트
        Category category1=Category.builder()
                .name("종합설계1")
                .build();
        categoryRepository.save(category1);

        // 카테고리2 테스트
        Category category2=Category.builder()
                .name("종합설계2")
                .build();
        categoryRepository.save(category2);


        List<PostCategory> categories=new ArrayList<>();


        // 게시물 생성
        Post post = Post.builder()
                .title("게시물 제목")
                .content("게시물 내용")
                .price(10000)
                .location("서울")
                .status(TradeStatus.WAITING)
                .views(0)
                .categories(categories)
                .build();

        PostCategory p1 = new PostCategory();
        p1.setPost(post);
        p1.setCategory(category1);

        PostCategory p2=new PostCategory();
        p2.setPost(post);
        p2.setCategory(category2);



        post.getCategories().add(p1);
        post.getCategories().add(p2);


        Post savedPost = postRepository.save(post);

        System.out.println("결과 "+savedPost.getCategories().get(0).getCategory().getName());
        System.out.println("결과 "+savedPost.getTitle());

    }
}