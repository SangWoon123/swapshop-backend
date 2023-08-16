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
@AllArgsConstructor
@Builder
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_id")
    private Login sender;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receiver_id")
    private Login receiver;

    private LocalDateTime createdAt;

    @Column(nullable = false)
    private boolean deletedBySender;

    @Column(nullable = false)
    private boolean deleteByReceiver;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "messageRoom_id")
    private MessageRoom messageRoom;

    public void deleteBySender(){
        this.deletedBySender=true;
    }

    public void deleteByReceiver(){
        this.deleteByReceiver=true;
    }

    public boolean isDeleted(){
        return isDeletedBySender() && isDeleteByReceiver();
    }

}
