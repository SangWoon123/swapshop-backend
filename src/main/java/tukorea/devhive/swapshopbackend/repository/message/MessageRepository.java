package tukorea.devhive.swapshopbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tukorea.devhive.swapshopbackend.model.dao.Message;
import tukorea.devhive.swapshopbackend.model.dao.login.Login;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message,Long> {
    List<Message> findAllByReceiver(Login user);
    List<Message> findAllBySender(Login user);
}
