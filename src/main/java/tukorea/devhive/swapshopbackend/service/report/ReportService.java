package tukorea.devhive.swapshopbackend.service.report;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tukorea.devhive.swapshopbackend.model.dao.login.Login;
import tukorea.devhive.swapshopbackend.model.dao.report.PostReport;
import tukorea.devhive.swapshopbackend.model.dao.report.UserReport;
import tukorea.devhive.swapshopbackend.model.dto.login.LoginDTO;
import tukorea.devhive.swapshopbackend.model.dto.report.PostReportDTO;
import tukorea.devhive.swapshopbackend.model.dto.report.UserReportDTO;
import tukorea.devhive.swapshopbackend.repository.login.LoginRepository;
import tukorea.devhive.swapshopbackend.repository.report.ReportRepository;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class ReportService {

    private final ReportRepository reportRepository;
    private final LoginRepository loginRepository;

    @Transactional
    public PostReportDTO reportPost(PostReportDTO postReportDto, LoginDTO userDto) {

        Login postReporter = loginRepository.findByNickname(userDto.getNickname());

        PostReport postReport = PostReport.builder()
                .postReporter(postReporter)
                .content(postReportDto.getContent())
                .createdAt(LocalDateTime.now())
                .build();

        return PostReportDTO.toDto(postReport);
    }

    @Transactional
    public UserReportDTO reportUser(UserReportDTO userReportDto, LoginDTO userDto) {

        Login userReporter = loginRepository.findByNickname(userDto.getNickname());

        UserReport userReport = UserReport.builder()
                .userReporter(userReporter)
                .content(userReportDto.getContent())
                .createdAt(LocalDateTime.now())
                .build();

        return UserReportDTO.toDto(userReport);
    }
}
