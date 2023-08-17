package tukorea.devhive.swapshopbackend.model.dto.message;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import tukorea.devhive.swapshopbackend.model.dao.login.Login;
import tukorea.devhive.swapshopbackend.model.dto.login.LoginDTO;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MessageRoomDTO {
    private Long id;
    private Login sender;
    private Login receiver;
    private MessageDTO lastMessage;
}
