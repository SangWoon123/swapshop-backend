package tukorea.devhive.swapshopbackend.controller.message;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import tukorea.devhive.swapshopbackend.model.dto.login.LoginDTO;
import tukorea.devhive.swapshopbackend.model.dto.message.InboxDTO;
import tukorea.devhive.swapshopbackend.model.dto.message.MessageDTO;
import tukorea.devhive.swapshopbackend.model.dto.message.RoomDTO;
import tukorea.devhive.swapshopbackend.repository.login.LoginRepository;
import tukorea.devhive.swapshopbackend.service.message.MessageService;

import java.util.List;



/**
 * 해당 클래스는 '쪽지함' 기능을 구현한 클래스로 쪽지함 기능 구현을 위해
 * 도메인으로 '쪽지' 와 '쪽지함'이 존재함으로 참고
 *
 * 반환 형태를 보고싶으면 https://www.notion.so/fcb909b9af5241cbb635555ca1c31701?pvs=4 을 참조해주세요.
 *
 * @author sangwoon kim
 * @version 1.0
 * 23/08/16
 */

@RestController
@RequiredArgsConstructor
@RequestMapping("/messages")
@CrossOrigin(origins = "http://localhost:3000")
public class MessageController {

    private final MessageService messageService;

    /**
     * 쪽지생성 컨트롤러
     *
     * @param MessageDTO messageDTO 필요한 메시지값 (MessageDTO 참조)
     * @param LoginDTO userDto 로그인한 유저
     * @return MessageDTO
     */
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping()
    public MessageDTO sendMessage(@RequestBody MessageDTO messageDTO, @AuthenticationPrincipal LoginDTO userDto){
        // 로그인한 유저를 가져와 sender로 설정
        messageDTO.setSenderName(userDto.getNickname());
        return messageService.write(messageDTO,userDto);
    }

    /**
     * 모든쪽지함 조회 컨트롤러로 로그인한 유저의 모든 쪽지함을 WrapperBox 클래스로 한번 감싸서 반환
     *
     * @param LoginDTO userDto 로그인한 유저
     * @return 로그인한 유저에게 온 모든 쪽지함 조회 (RoomDTO 참조)
     *
     */
    @GetMapping()
    public WrapperBox<List<RoomDTO>> findAllRoom(@AuthenticationPrincipal LoginDTO userDto){
        return new WrapperBox<>(messageService.allMessage(userDto));
    }


    /**
     * 로그인한 유저에게 온 개별 쪽지함을 확인하는 컨트롤러로 WrapperBoxDetail 클래스로 한번 감싸서 반환
     * 쪽지함 id로 조회시 유저에게 받은쪽지는 '받은 메시지' 로표기 보낸 쪽지는 '보낸 메시지' 로 표기
     *
     * @param Long id 쪽지함 id
     * @param LoginDTO userDto 로그인한 유저
     * @return List<InboxDTO> 참조
     */
    @GetMapping("/{room_id}")
    public WrapperBoxDetail<List<InboxDTO>> search(@PathVariable(value = "room_id") Long id,@AuthenticationPrincipal LoginDTO userDto){
        return new WrapperBoxDetail<>(messageService.messageSearch(id,userDto));
    }


    /**
     * 개별 쪽지를 삭제하는 컨트롤러
     *
     * @param Long messageId 쪽지 id
     * @param LoginDTO userDto 로그인한 유저
     * @return X
     */
    @DeleteMapping("/{room_id}/{message_id}")
    public void deleteMessage(@PathVariable(value = "message_id") Long messageId,@AuthenticationPrincipal LoginDTO userDto){
        messageService.deleteMessage(messageId,userDto);
    }

    /**
     * 쪽지함을 삭제하는 컨트롤러
     *
     * @param Long roomId 쪽지 id
     * @param LoginDTO userDto 로그인한 유저
     * @return X
     */
    @DeleteMapping("/{room_id}")
    public void deleteMessageRoom(@PathVariable(value = "room_id") Long roomId,@AuthenticationPrincipal LoginDTO userDto){
        messageService.deleteMessageRoom(roomId,userDto);
    }

}
