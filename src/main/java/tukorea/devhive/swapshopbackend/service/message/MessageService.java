package tukorea.devhive.swapshopbackend.service;

import com.amazonaws.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tukorea.devhive.swapshopbackend.model.dao.Message;
import tukorea.devhive.swapshopbackend.model.dao.login.Login;
import tukorea.devhive.swapshopbackend.model.dto.MessageDTO;
import tukorea.devhive.swapshopbackend.model.dto.login.LoginDTO;
import tukorea.devhive.swapshopbackend.repository.MessageRepository;
import tukorea.devhive.swapshopbackend.repository.login.LoginRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class MessageService {
    private final MessageRepository messageRepository;
    private final LoginRepository loginRepository;

    @Transactional
    public MessageDTO write(MessageDTO messageDto, LoginDTO userDto){

        // 받는사람
        Login receiver=loginRepository.findByNickname(messageDto.getReceiverName());

        // 보낸사람
        Login sender=loginRepository.findByNickname(userDto.getNickname());

        // 메세지 작성
        Message message= Message.builder()
                .receiver(receiver)
                .sender(sender)
                .title(messageDto.getTitle())
                .content(messageDto.getContent())
                .createdAt(LocalDateTime.now())
                .build();

        messageRepository.save(message);

        return MessageDTO.toDto(message);
    }

    @Transactional(readOnly = true)
    public List<MessageDTO> receiveMessage(Login user) {
        List<Message> messages=messageRepository.findAllByReceiver(user);
        List<MessageDTO> messageDtos=new ArrayList<>();

        for(Message message: messages){
            messageDtos.add(MessageDTO.toDto(message));
        }

        return messageDtos;
    }

    @Transactional
    public void deleteMessageByReceiver(Long id, Login user) {
        Message ReceiveMessage = messageRepository.findById(id)
                .orElseThrow(()->new IllegalArgumentException("메시지를 찾을 수 없습니다."));

        messageRepository.delete(ReceiveMessage);
    }
}
