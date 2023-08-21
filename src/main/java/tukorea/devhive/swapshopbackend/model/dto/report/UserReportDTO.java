package tukorea.devhive.swapshopbackend.model.dto.report;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tukorea.devhive.swapshopbackend.model.dao.report.UserReport;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserReportDTO {

    private Long id;
    private Long reportedUserId;
    private String content;

    public static UserReportDTO toDto(UserReport userReport){
        return new UserReportDTO(
                userReport.getId(),
                userReport.getReportedUserId(),
                userReport.getContent()
        );
    }
}
