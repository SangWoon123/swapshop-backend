package tukorea.devhive.swapshopbackend.repository.report;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tukorea.devhive.swapshopbackend.model.dao.report.PostReport;

import java.util.List;

@Repository
public interface PostReportRepository extends JpaRepository<PostReport,Long> {
    List<PostReport> findByPostReporterIdAndReportedPostId(Long postReporterId, Long reportedPostId);

    void deleteByReportedPostId(Long postId);
}