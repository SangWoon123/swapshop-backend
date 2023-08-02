package tukorea.devhive.swapshopbackend.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import tukorea.devhive.swapshopbackend.model.dao.Message;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageDTO {

    private String title;
    private String content;
    private String senderName;
    private String receiverName;

    public static MessageDTO toDto(Message message){
        return new MessageDTO(
                message.getTitle(),
                message.getContent(),
                message.getSender().getNickname(),
                message.getReceiver().getNickname()
        );
    }
}
