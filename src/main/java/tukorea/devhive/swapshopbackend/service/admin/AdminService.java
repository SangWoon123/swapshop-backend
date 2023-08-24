package tukorea.devhive.swapshopbackend.service.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tukorea.devhive.swapshopbackend.model.dao.login.Login;
import tukorea.devhive.swapshopbackend.model.dao.post.Post;
import tukorea.devhive.swapshopbackend.model.dao.report.PostReport;
import tukorea.devhive.swapshopbackend.model.dao.report.UserReport;
import tukorea.devhive.swapshopbackend.model.dto.report.PostReportDTO;
import tukorea.devhive.swapshopbackend.model.dto.report.UserReportDTO;
import tukorea.devhive.swapshopbackend.repository.login.LoginRepository;
import tukorea.devhive.swapshopbackend.repository.post.PostRepository;
import tukorea.devhive.swapshopbackend.repository.report.PostReportRepository;
import tukorea.devhive.swapshopbackend.repository.report.UserReportRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AdminService {
    private final PostReportRepository postReportRepository;
    private final UserReportRepository userReportRepository;
    private final PostRepository postRepository;
    private final LoginRepository loginRepository;

    @Autowired
    public AdminService(PostReportRepository postReportRepository, UserReportRepository userReportRepository,
                        PostRepository postRepository, LoginRepository loginRepository) {
        this.postReportRepository = postReportRepository;
        this.userReportRepository = userReportRepository;
        this.postRepository = postRepository;
        this.loginRepository = loginRepository;
    }

    // 신고된 게시글 목록 조회
    public List<PostReportDTO> getReportedPosts() {
        List<PostReport> reportedPosts = postReportRepository.findAll();
        return reportedPosts.stream()
                .map(PostReportDTO::toDto)
                .collect(Collectors.toList());
    }


    // 신고된 게시글 삭제
    @Transactional
    public void deleteReportedPost(Long postId) {
        Optional<Post> postOptional = postRepository.findById(postId);
        if (postOptional.isPresent()) {
            postRepository.deleteById(postId);
            postReportRepository.deleteByReportedPostId(postId);
        }
    }

    // 신고된 유저 목록 조회
    public List<UserReportDTO> getReportedUsers() {
        List<UserReport> reportedUsers = userReportRepository.findAll();
        return reportedUsers.stream()
                .map(UserReportDTO::toDto)
                .collect(Collectors.toList());
    }

    // 신고된 유저 삭제
    @Transactional
    public void deleteReportedUser(Long userId) {
        Optional<Login> userOptional = loginRepository.findById(userId);
        if (userOptional.isPresent()) {
            loginRepository.deleteById(userId);
            userReportRepository.deleteByReportedUserId(userId);
        }
    }
}
