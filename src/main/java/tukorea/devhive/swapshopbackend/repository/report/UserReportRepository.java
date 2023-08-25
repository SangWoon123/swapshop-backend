package tukorea.devhive.swapshopbackend.repository.report;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tukorea.devhive.swapshopbackend.model.dao.report.UserReport;

import java.util.List;

@Repository
public interface UserReportRepository extends JpaRepository<UserReport,Long> {
    List<UserReport> findByUserReporterIdAndReportedUserId(Long userReporterId, Long reportedUserId);

    void deleteByReportedUserId(Long userId);
}