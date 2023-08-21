package tukorea.devhive.swapshopbackend.service.report;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tukorea.devhive.swapshopbackend.exeption.ReportAlreadyExistsException;
import tukorea.devhive.swapshopbackend.model.dao.login.Login;
import tukorea.devhive.swapshopbackend.model.dao.report.PostReport;
import tukorea.devhive.swapshopbackend.model.dao.report.UserReport;
import tukorea.devhive.swapshopbackend.model.dto.login.LoginDTO;
import tukorea.devhive.swapshopbackend.model.dto.report.PostReportDTO;
import tukorea.devhive.swapshopbackend.model.dto.report.UserReportDTO;
import tukorea.devhive.swapshopbackend.repository.login.LoginRepository;
import tukorea.devhive.swapshopbackend.repository.report.PostReportRepository;
import tukorea.devhive.swapshopbackend.repository.report.UserReportRepository;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ReportService {

    private final PostReportRepository postReportRepository;
    private final UserReportRepository userReportRepository;
    private final LoginRepository loginRepository;

    @Transactional
    public PostReportDTO reportPost(PostReportDTO postReportDto, LoginDTO userDto) {
        // 이미 신고한 게시글 또 신고 x
        List<PostReport> existingPostReports = postReportRepository.findByPostReporterIdAndReportedPostId(
                userDto.getUserId(), postReportDto.getReportedPostId());

        if (!existingPostReports.isEmpty()) {
            throw new ReportAlreadyExistsException("이미 해당 게시글을 신고하였습니다.");
        }

        Login postReporter = loginRepository.findByNickname(userDto.getNickname());

        PostReport postReport = PostReport.builder()
                .reportedPostId(postReportDto.getReportedPostId())
                .postReporter(postReporter)
                .content(postReportDto.getContent())
                .createdAt(LocalDateTime.now())
                .build();

        postReportRepository.save(postReport);

        return PostReportDTO.toDto(postReport);
    }

    @Transactional
    public UserReportDTO reportUser(UserReportDTO userReportDto, LoginDTO userDto) {
        // 이미 신고한 유저 또 신고 x
        List<UserReport> existingUserReports = userReportRepository.findByUserReporterIdAndReportedUserId(
                userDto.getUserId(), userReportDto.getReportedUserId());

        if (!existingUserReports.isEmpty()) {
            throw new ReportAlreadyExistsException("이미 해당 사용자를 신고하였습니다.");
        }

        // 자기 자신 신고 x
        if (userDto.getUserId().equals(userReportDto.getReportedUserId())) {
            throw new IllegalArgumentException("자기 자신을 신고할 수 없습니다.");
        }

        Login userReporter = loginRepository.findByNickname(userDto.getNickname());

        UserReport userReport = UserReport.builder()
                .reportedUserId(userReportDto.getReportedUserId())
                .content(userReportDto.getContent())
                .userReporter(userReporter)
                .createdAt(LocalDateTime.now())
                .build();

        UserReport savedUserReport = userReportRepository.save(userReport);

        UserReportDTO responseUserReportDto = UserReportDTO.toDto(savedUserReport);

        return responseUserReportDto;
    }
}
