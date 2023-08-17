package tukorea.devhive.swapshopbackend.model.dto.message;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import tukorea.devhive.swapshopbackend.model.dao.message.Message;
import tukorea.devhive.swapshopbackend.model.dao.message.MessageRoom;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageDTO {

    private Long id;
    private String content;
    private String senderName;
    private String receiverName;
    private Long room;
    private LocalDateTime createdAt;

    public static MessageDTO toDto(Message message){
        return new MessageDTO(
                message.getId(),
                message.getContent(),
                message.getSender().getNickname(),
                message.getReceiver().getNickname(),
                message.getMessageRoom().getId(),
                message.getCreatedAt()
        );
    }
}
