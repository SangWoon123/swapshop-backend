package tukorea.devhive.swapshopbackend.model.dto.message;

import lombok.*;
import tukorea.devhive.swapshopbackend.model.dao.message.Message;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InboxDTO {
    private Long id;
    private String state; // 보낸 상태
    private String lastMessage;
    private LocalDateTime lastDate;

    public static InboxDTO toDto(Message message) {
        return InboxDTO.builder()
                .id(message.getId())
                .lastMessage(message.getContent())
                .lastDate(message.getCreatedAt())
                .build();
    }
}

