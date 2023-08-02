package tukorea.devhive.swapshopbackend.controller;

import com.amazonaws.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import tukorea.devhive.swapshopbackend.model.dao.login.Login;
import tukorea.devhive.swapshopbackend.model.dto.MessageDTO;
import tukorea.devhive.swapshopbackend.model.dto.login.LoginDTO;
import tukorea.devhive.swapshopbackend.repository.login.LoginRepository;
import tukorea.devhive.swapshopbackend.service.MessageService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/messages")
public class MessageController {

    private final MessageService messageService;
    private final LoginRepository loginRepository;

    // 쪽지 작성
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping()
    public MessageDTO sendMessage(@RequestBody MessageDTO messageDTO, @AuthenticationPrincipal LoginDTO userDto){
        // 로그인한 유저를 가져와 sender로 설정
        messageDTO.setSenderName(userDto.getNickname());
        return messageService.write(messageDTO,userDto);
    }

    // 받은 쪽지 불러오기
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/received")
    public List<MessageDTO> getReceivedMessage(@AuthenticationPrincipal LoginDTO userDto){

        Login user = loginRepository.findByNickname(userDto.getNickname());
        return messageService.receiveMessage(user);
    }

    // 쪽지 삭제
    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/received/{id}")
    public void deleteReceivedMessage(@PathVariable(name = "id") Long id,@AuthenticationPrincipal LoginDTO userDto){
        Login user = loginRepository.findByNickname(userDto.getNickname());
        messageService.deleteMessageByReceiver(id, user);
    }

    // WebSocket 메시지 핸들러
    @MessageMapping("/sendMessage")
    @SendTo("/topic/messages")
    public MessageDTO handleWebSocketMessage(@RequestBody MessageDTO messageDTO) {
        return ;
    }



}
