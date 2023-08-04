package tukorea.devhive.swapshopbackend.controller.chat;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ChatDto {

    public enum MessageType{
        ENTER,TALK
    }

    private MessageType type;
    private String roomId;
    private String sender;
    private String message;
    private String time;
}
