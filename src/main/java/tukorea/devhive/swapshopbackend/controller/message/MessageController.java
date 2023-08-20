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

//        설명: 쪽지함의 구현중 컨트롤러에 해당하는 부분 ,
//        쪽지함에는 채팅방처럼 다른 유저와 대화한 내역들을 볼수있고, 쪽지함에는 다른 유저닉네임과 최신메시지와 시간을 확인 할수 있고,
//        예를 들어 쪽지함에는 a,b,c,d 유저와의 대화내역이 존재하고, a 유저와의 쪽지함을 클릭하면 a와 대화한 내역들을 볼 수 있게된다.
//        자세한 설명은 노션 링크: https://www.notion.so/fcb909b9af5241cbb635555ca1c31701?pvs=4

//        기능 5가지
//          1. 쪽지생성
//          2. 모든 쪽지함 조회
//          3. 쪽지함 개별 조회
//          4. 개별 쪽지 삭제
//          5. 쪽지함 삭제
//
//        관련 클래스:
//        Domain
//           - Message / MessageRoom
//        DTO
//           - InboxDTO ( 쪽지함 리턴 클래스 ) / MessageDTO ( 쪽지 생성시 리턴 클래스 ) / RoomDTO ( 모든 쪽지함 조회 클래스) / MessageRoomDTO ( 쪽지함 개별 조회시 클래스 )
//        Service
//           - write ( 쪽지 생성 ) / allMessage ( 모든 쪽지함 조회 ) / messageSearch ( 쪽지함 id로 쪽지 조회 ) / createOrFindMessageRoom ( receiver와 sender 간의 쪽지함 여부 ) / deleteMessage ( 쪽지 삭제 ) / deleteMessageRoom ( 쪽지함 삭제 )
//        Repository
//           -MessageRepository / MessageRoomRepository

//        작성일: 23/08/16

@RestController
@RequiredArgsConstructor
@RequestMapping("/messages")
@CrossOrigin(origins = "http://localhost:3000")
public class MessageController {

    private final MessageService messageService;

    // 쪽지 작성
    // 예외 추후 추가: 스스로 전송할경우 오류 발생시켜야함
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
