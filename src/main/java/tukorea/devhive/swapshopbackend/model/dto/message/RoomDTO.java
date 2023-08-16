package tukorea.devhive.swapshopbackend.model.dto.message;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RoomDTO {
    private Long id;
    private String nickname;
    private String lastMessage;
    private LocalDateTime lastDate;
}
