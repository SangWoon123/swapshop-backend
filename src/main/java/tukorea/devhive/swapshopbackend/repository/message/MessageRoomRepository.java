package tukorea.devhive.swapshopbackend.repository.message;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tukorea.devhive.swapshopbackend.model.dao.login.Login;
import tukorea.devhive.swapshopbackend.model.dao.message.Message;
import tukorea.devhive.swapshopbackend.model.dao.message.MessageRoom;

import java.util.List;
import java.util.Optional;

@Repository
public interface MessageRoomRepository extends JpaRepository<MessageRoom,Long> {

    Optional<MessageRoom> findMessageRoomByUserAAndUserB(Login userA, Login userB);


}
