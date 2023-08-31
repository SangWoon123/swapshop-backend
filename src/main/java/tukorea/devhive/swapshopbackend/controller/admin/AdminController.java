package tukorea.devhive.swapshopbackend.controller.admin;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tukorea.devhive.swapshopbackend.model.dto.report.PostReportDTO;
import tukorea.devhive.swapshopbackend.model.dto.report.UserReportDTO;
import tukorea.devhive.swapshopbackend.service.admin.AdminService;

import java.util.List;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
@Tag(name = "Admin", description = "관리자 API Document")
public class AdminController {

    private final AdminService adminService;

    // 신고된 게시글 목록 조회
    @Operation(summary = "신고된 게시글 목록 조회", description = "신고된 게시글 목록을 조회합니다.")
    @GetMapping("/posts")
    public ResponseEntity<List<PostReportDTO>> getReportedPosts() {
        List<PostReportDTO> reportedPosts = adminService.getReportedPosts();
        return ResponseEntity.ok(reportedPosts);
    }

    // 신고된 게시글 삭제
    @Operation(summary = "신고된 게시글 삭제", description = "신고된 게시글을 삭제합니다.")
    @DeleteMapping("/posts/{postId}")
    public ResponseEntity<Void> deleteReportedPost(@PathVariable Long postId) {
        adminService.deleteReportedPost(postId);
        return ResponseEntity.noContent().build();
    }

    // 신고된 유저 목록 조회
    @Operation(summary = "신고된 유저 목록 조회", description = "신고된 유저 목록을 조회합니다.")
    @GetMapping("/users")
    public ResponseEntity<List<UserReportDTO>> getReportedUsers() {
        List<UserReportDTO> reportedUsers = adminService.getReportedUsers();
        return ResponseEntity.ok(reportedUsers);
    }

    // 신고된 유저 삭제
    @Operation(summary = "신고된 유저 삭제", description = "신고된 유저 목록을 삭제합니다.")
    @DeleteMapping("/users/{userId}")
    public ResponseEntity<Void> deleteReportedUser(@PathVariable Long userId) {
        adminService.deleteReportedUser(userId);
        return ResponseEntity.noContent().build();
    }
}
