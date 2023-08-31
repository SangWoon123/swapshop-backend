package tukorea.devhive.swapshopbackend.controller.report;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Report", description = "신고 API Document")
public class ReportController {

    private final ReportService reportService;

    // 게시글 신고
    @Operation(summary = "게시글 신고", description = "게시글을 신고합니다.")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/post")
    public PostReportDTO reportPost(@RequestBody PostReportDTO postReportDTO,
                                    @AuthenticationPrincipal LoginDTO userDto) {
        postReportDTO.setId(userDto.getUserId());
        return reportService.reportPost(postReportDTO, userDto);
    }

    // 유저 신고
    @Operation(summary = "유저 신고", description = "유저를 신고합니다.")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/user")
    public UserReportDTO reportUser(@RequestBody UserReportDTO userReportDTO,
                                    @AuthenticationPrincipal LoginDTO userDto) {
        userReportDTO.setId(userDto.getUserId());
        return reportService.reportUser(userReportDTO, userDto);
    }
}

