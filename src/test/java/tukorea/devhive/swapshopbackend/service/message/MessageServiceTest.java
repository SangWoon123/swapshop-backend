package tukorea.devhive.swapshopbackend.service.message;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import tukorea.devhive.swapshopbackend.model.Enum.login.AuthenticationType;
import tukorea.devhive.swapshopbackend.model.dao.login.Login;
import tukorea.devhive.swapshopbackend.model.dao.message.Message;
import tukorea.devhive.swapshopbackend.model.dao.message.MessageRoom;
import tukorea.devhive.swapshopbackend.model.dto.login.LoginDTO;
import tukorea.devhive.swapshopbackend.model.dto.message.MessageDTO;
import tukorea.devhive.swapshopbackend.repository.login.LoginRepository;
import tukorea.devhive.swapshopbackend.repository.message.MessageRepository;
import tukorea.devhive.swapshopbackend.repository.message.MessageRoomRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@Transactional
class MessageServiceTest {

    @Autowired
    private MessageService messageService;

    @Autowired
    private LoginRepository loginRepository;

    @Autowired
    private MessageRoomRepository messageRoomRepository;

    @Autowired
    private MessageRepository messageRepository;

//    @Test
//    public void messageRoomCheck(){
//        //given
//        Login receiver=Login.builder()
//                .email("test1@google.com")
//                .authType(AuthenticationType.GOOGLE)
//                .nickname("test1")
//                .build();
//
//        Login sender=Login.builder()
//                .email("test2@kakao.com")
//                .authType(AuthenticationType.KAKAO)
//                .nickname("test2")
//                .build();
//
//        loginRepository.save(receiver);
//        loginRepository.save(sender);
//
//        List<Message> a=new ArrayList<>();
//
//        MessageRoom messageRoom=
//                MessageRoom.builder()
//                        .userB(receiver)
//                        .userA(sender)
//                        .build();
//
//        MessageRoom room = messageRoomRepository.save(messageRoom);
//
//
//        Message message=Message.builder()
//                .messageRoom(messageRoom)
//                .receiver(receiver)
//                .sender(sender)
//                .content("테스트")
//                .createdAt(LocalDateTime.now())
//                .build();
//
//        Message message2=Message.builder()
//                .messageRoom(messageRoom)
//                .receiver(sender)
//                .sender(receiver)
//                .content("테스트인데요")
//                .createdAt(LocalDateTime.now())
//                .build();
//
//        a.add(message);
//        a.add(message2);
//
//        messageRoom.setMessages(a);
//
//
//
//        message2Repository.save(message);
//
//        //when
//        Long roomId = room.getId();
//        messageService.messageSearch(roomId);
//
//
//
//    }

    @Test
    public void getRoom(){
        //given
        Login user=Login.builder()
                .email("test1@google.com")
                .authType(AuthenticationType.GOOGLE)
                .nickname("test1")
                .build();

        Login test2=Login.builder()
                .email("test2@kakao.com")
                .authType(AuthenticationType.KAKAO)
                .nickname("test2")
                .build();

        Login test3=Login.builder()
                .email("test2@kakao.com")
                .authType(AuthenticationType.KAKAO)
                .nickname("test3")
                .build();

        loginRepository.save(user);
        loginRepository.save(test2);
        loginRepository.save(test3);

        List<Message> a=new ArrayList<>();

        MessageRoom messageRoom=
                MessageRoom.builder()
                        .userB(user)
                        .userA(test2)
                        .build();

        MessageRoom room = messageRoomRepository.save(messageRoom);


        Message message=Message.builder()
                .messageRoom(messageRoom)
                .receiver(user)
                .sender(test2)
                .content("테스트")
                .createdAt(LocalDateTime.now())
                .build();

        Message message2=Message.builder()
                .messageRoom(messageRoom)
                .receiver(test2)
                .sender(user)
                .content("user가 직접 보낸 메시지 받는자는 test2")
                .createdAt(LocalDateTime.now())
                .build();

        Message message5=Message.builder()
                .messageRoom(messageRoom)
                .receiver(test2)
                .sender(user)
                .content("user가 직접 보낸 메시지 받는자는 test2 최근메시지")
                .createdAt(LocalDateTime.now())
                .build();

        Message message3=Message.builder()
                .messageRoom(messageRoom)
                .receiver(user)
                .sender(test3)
                .content("test3가 직접 보낸 메시지 받는자는 user")
                .createdAt(LocalDateTime.now())
                .build();

        Message message4=Message.builder()
                .messageRoom(messageRoom)
                .receiver(user)
                .sender(test3)
                .content("test3가 직접 보낸 메시지 받는자는 user 최근 내용")
                .createdAt(LocalDateTime.now())
                .build();

        a.add(message);
        a.add(message2);
        a.add(message3);
        a.add(message4);
        a.add(message5);

        messageRoom.setMessages(a);

        messageRepository.save(message);
        messageRepository.save(message2);
        messageRepository.save(message3);
        messageRepository.save(message4);
        messageRepository.save(message5);

        messageService.allMessage(LoginDTO.mapToDto(user));



    }

    @Test
    public void selfMessageError(){
        //given
        Login user=Login.builder()
                .email("test1@google.com")
                .authType(AuthenticationType.GOOGLE)
                .nickname("test1")
                .build();

        loginRepository.save(user);

        MessageRoom messageRoom=
                MessageRoom.builder()
                        .userB(user)
                        .userA(user)
                        .build();

        MessageRoom room = messageRoomRepository.save(messageRoom);


        Message message=Message.builder()
                .messageRoom(messageRoom)
                .receiver(user)
                .sender(user)
                .content("테스트")
                .createdAt(LocalDateTime.now())
                .build();



        assertThrows(IllegalArgumentException.class,()->
                messageService.write(MessageDTO.toDto(message), LoginDTO.mapToDto(user)));

    }

    @Test
    public void messageRoomDelete(){
        //given
        Login user=Login.builder()
                .email("test1@google.com")
                .authType(AuthenticationType.GOOGLE)
                .nickname("test1")
                .build();

        Login test2=Login.builder()
                .email("test2@kakao.com")
                .authType(AuthenticationType.KAKAO)
                .nickname("test2")
                .build();

        loginRepository.save(user);
        loginRepository.save(test2);

        List<Message> a=new ArrayList<>();

        MessageRoom messageRoom=
                MessageRoom.builder()
                        .userB(user)
                        .userA(test2)
                        .build();

        MessageRoom room = messageRoomRepository.save(messageRoom);


        Message message=Message.builder()
                .messageRoom(messageRoom)
                .receiver(user)
                .sender(test2)
                .content("테스트")
                .createdAt(LocalDateTime.now())
                .build();

        Message message2=Message.builder()
                .messageRoom(messageRoom)
                .receiver(test2)
                .sender(user)
                .content("user가 직접 보낸 메시지 받는자는 test2")
                .createdAt(LocalDateTime.now())
                .build();

        a.add(message);
        a.add(message2);


        messageRoom.setMessages(a);

        messageRepository.save(message);
        messageRepository.save(message2);


        messageService.deleteMessageRoom(room.getId(),LoginDTO.mapToDto(user));
        messageService.deleteMessageRoom(room.getId(),LoginDTO.mapToDto(test2));



        // 메시지룸 삭제 여부 검증
        MessageRoom deletedRoom = messageRoomRepository.findById(room.getId()).orElse(null);
        assertNull(deletedRoom);


    }



}