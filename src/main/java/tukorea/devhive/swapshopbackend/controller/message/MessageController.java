package tukorea.devhive.swapshopbackend.controller.message;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import tukorea.devhive.swapshopbackend.model.dto.message.InboxDTO;
import tukorea.devhive.swapshopbackend.model.dto.message.MessageDTO;
import tukorea.devhive.swapshopbackend.model.dto.login.LoginDTO;
import tukorea.devhive.swapshopbackend.model.dto.message.RoomDTO;
import tukorea.devhive.swapshopbackend.repository.login.LoginRepository;
import tukorea.devhive.swapshopbackend.service.message.MessageService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/messages")
@CrossOrigin(origins = "http://localhost:3000")
public class MessageController {

    private final MessageService messageService;
    private final LoginRepository loginRepository;

    // 쪽지 작성
    // 스스로 전송할경우 오류 발생시켜얗ㅁ
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping()
    public MessageDTO sendMessage(@RequestBody MessageDTO messageDTO, @AuthenticationPrincipal LoginDTO userDto){
        // 로그인한 유저를 가져와 sender로 설정
        messageDTO.setSenderName(userDto.getNickname());
        return messageService.write(messageDTO,userDto);
    }

    // 모든 쪽지함 조회
    @GetMapping()
    public WrapperBox<List<RoomDTO>> findAllRoom(@AuthenticationPrincipal LoginDTO userDto){
        return new WrapperBox<>(messageService.allMessage(userDto));
    }

    // 쪽지함 디테일 -> 받은메시지,보낸 메시지 조회
    @GetMapping("/{room_id}")
    public WrapperBoxDetail<List<InboxDTO>> search(@PathVariable(value = "room_id") Long id,@AuthenticationPrincipal LoginDTO userDto){
        return new WrapperBoxDetail<>(messageService.messageSearch(id,userDto));
    }

    // 쪽지 삭제
    @DeleteMapping("/{room_id}/{message_id}")
    public void deleteMessage(@PathVariable(value = "message_id") Long messageId,@AuthenticationPrincipal LoginDTO userDto){
        messageService.deleteMessage(messageId,userDto);
    }

    // 쪽지함 자체 삭제
    @DeleteMapping("/{room_id}")
    public void deleteMessageRoom(@PathVariable(value = "room_id") Long roomId,@AuthenticationPrincipal LoginDTO userDto){
        messageService.deleteMessageRoom(roomId,userDto);
    }

}
