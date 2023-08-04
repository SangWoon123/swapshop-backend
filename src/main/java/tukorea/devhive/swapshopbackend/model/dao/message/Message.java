package tukorea.devhive.swapshopbackend.model.dao.message;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import tukorea.devhive.swapshopbackend.model.dao.login.Login;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_id")
    private Login sender;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receiver_id")
    private Login receiver;

    private LocalDateTime createdAt;

    @Builder
    public Message(Long id, String title, String content, Login sender, Login receiver, LocalDateTime createdAt) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.sender = sender;
        this.receiver = receiver;
        this.createdAt = createdAt;
    }
}
