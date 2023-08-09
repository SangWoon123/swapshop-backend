package tukorea.devhive.swapshopbackend.model.dao.report;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tukorea.devhive.swapshopbackend.model.dao.BaseTimeEntity;
import tukorea.devhive.swapshopbackend.model.dao.login.Login;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class PostReport extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long reportedPostId;

    @Column(nullable = false)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_reporter_id")
    private Login postReporter;

    private LocalDateTime createdAt;

    @Builder
    public PostReport(Long id, Long reportedPostId,String content, Login postReporter,LocalDateTime createdAt) {
        this.id = id;
        this.reportedPostId = reportedPostId;
        this.content = content;
        this.postReporter = postReporter;
        this.createdAt = createdAt;
    }

}
