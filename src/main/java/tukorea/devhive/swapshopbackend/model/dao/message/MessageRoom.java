package tukorea.devhive.swapshopbackend.model.dao.message;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import tukorea.devhive.swapshopbackend.model.dao.login.Login;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MessageRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userA_id")
    private Login userA;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userB_id")
    private Login userB;

    @OneToMany(mappedBy = "messageRoom",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Message> messages=new ArrayList<>();

    @Column(nullable = false)
    private boolean deletedByUserA;

    @Column(nullable = false)
    private boolean deleteByUserB;

    public void deletedByUserA(){
        this.deletedByUserA=true;
    }

    public void deleteByUserB(){
        this.deleteByUserB=true;
    }

    public boolean isDeleted(){
        return isDeletedByUserA() && isDeleteByUserB();
    }
    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }
}
