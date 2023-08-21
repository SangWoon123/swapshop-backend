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
public class UserReport extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long reportedUserId;

    @Column(nullable = false)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_reporter_id")
    private Login userReporter;

    private LocalDateTime createdAt;

    @Builder
    public UserReport(Long id, Long reportedUserId, String content, Login userReporter, LocalDateTime createdAt) {
        this.id = id;
        this.reportedUserId = reportedUserId;
        this.content = content;
        this.userReporter = userReporter;
        this.createdAt = createdAt;
    }
}
