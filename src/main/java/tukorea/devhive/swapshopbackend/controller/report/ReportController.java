package tukorea.devhive.swapshopbackend.controller.report;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import tukorea.devhive.swapshopbackend.model.dto.login.LoginDTO;
import tukorea.devhive.swapshopbackend.model.dto.report.PostReportDTO;
import tukorea.devhive.swapshopbackend.model.dto.report.UserReportDTO;
import tukorea.devhive.swapshopbackend.service.report.ReportService;

@RestController
@RequestMapping("/report")
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;

    // 게시글 신고
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/post")
    public PostReportDTO reportPost(@RequestBody PostReportDTO postReportDTO,
                                    @AuthenticationPrincipal LoginDTO userDto) {
        postReportDTO.setId(userDto.getUserId());
        return reportService.reportPost(postReportDTO, userDto);
    }

    // 유저 신고
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/user")
    public UserReportDTO reportUser(@RequestBody UserReportDTO userReportDTO,
                                    @AuthenticationPrincipal LoginDTO userDto) {
        userReportDTO.setId(userDto.getUserId());
        return reportService.reportUser(userReportDTO, userDto);
    }
}

