package tukorea.devhive.swapshopbackend.repository.message;

import org.springframework.data.jpa.repository.JpaRepository;
import tukorea.devhive.swapshopbackend.model.dao.message.Message;
import tukorea.devhive.swapshopbackend.model.dao.login.Login;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message,Long> {
    List<Message> findAllByReceiver(Login user);
    List<Message> findAllBySender(Login user);

    Message findTopBySenderAndReceiverOrSenderAndReceiverOrderByCreatedAtDesc(Login sender1, Login receiver1, Login sender2, Login receiver2);
}
