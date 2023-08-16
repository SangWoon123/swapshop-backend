package tukorea.devhive.swapshopbackend.model.dto.report;

import lombok.*;
import tukorea.devhive.swapshopbackend.model.dao.report.PostReport;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PostReportDTO {

    private Long reportedPostId;
    private String content;

    public static PostReportDTO toDto(PostReport postReport){
        return new PostReportDTO(
                postReport.getReportedPostId(),
                postReport.getContent()
        );
    }
}


