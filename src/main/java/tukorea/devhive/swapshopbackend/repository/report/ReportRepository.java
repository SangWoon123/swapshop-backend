package tukorea.devhive.swapshopbackend.repository.report;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tukorea.devhive.swapshopbackend.model.dao.report.PostReport;

@Repository
public interface ReportRepository extends JpaRepository<PostReport,Long> {
}
