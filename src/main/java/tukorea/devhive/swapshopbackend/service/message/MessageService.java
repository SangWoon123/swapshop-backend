package tukorea.devhive.swapshopbackend.service.message;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tukorea.devhive.swapshopbackend.model.dao.message.Message;
import tukorea.devhive.swapshopbackend.model.dao.login.Login;
import tukorea.devhive.swapshopbackend.model.dao.message.MessageRoom;
import tukorea.devhive.swapshopbackend.model.dto.message.InboxDTO;
import tukorea.devhive.swapshopbackend.model.dto.message.MessageDTO;
import tukorea.devhive.swapshopbackend.model.dto.login.LoginDTO;
import tukorea.devhive.swapshopbackend.model.dto.message.RoomDTO;
import tukorea.devhive.swapshopbackend.repository.login.LoginRepository;
import tukorea.devhive.swapshopbackend.repository.message.MessageRepository;
import tukorea.devhive.swapshopbackend.repository.message.MessageRoomRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class MessageService {
    private final MessageRepository messageRepository;
    private final LoginRepository loginRepository;
    private final MessageRoomRepository messageRoomRepository;


    @Transactional
    public MessageDTO write(MessageDTO messageDto, LoginDTO userDto){

        // 받는사람
        Login receiver=loginRepository.findByNickname(messageDto.getReceiverName());

        // 보낸사람
        Login sender=loginRepository.findByNickname(userDto.getNickname());

        // 자기 자신에게 쪽지를 보내는 경우 예외 처리
        if (sender.equals(receiver)) {
            throw new IllegalArgumentException("자기 자신에게 쪽지를 보낼 수 없습니다.");
        }

        // 쪽지함 생성 또는 찾기 = 쪽지함 반환
        MessageRoom messageRoom = createOrFindMessageRoom(sender, receiver);

        // 메세지 작성
        Message message= Message.builder()
                .receiver(receiver)
                .sender(sender)
                .content(messageDto.getContent())
                .createdAt(LocalDateTime.now())
                .messageRoom(messageRoom)
                .build();

        messageRepository.save(message);

        return MessageDTO.toDto(message);
    }

    @Transactional
    public List<RoomDTO> allMessage(LoginDTO userDto){

        //로그인한유저 정보
        Login user=loginRepository.findByNickname(userDto.getNickname());

        List<String> otherUserNicknames = new ArrayList<>();

        List<RoomDTO> roomDTOList = new ArrayList<>();

        List<Message> sentMessages = messageRepository.findAllBySender(user);
        for (Message message : sentMessages) {
            if (!otherUserNicknames.contains(message.getReceiver().getNickname())) {
                otherUserNicknames.add(message.getReceiver().getNickname());
            }
        }

        List<Message> receivedMessages = messageRepository.findAllByReceiver(user);
        for (Message message : receivedMessages) {
            if (!otherUserNicknames.contains(message.getSender().getNickname())) {
                otherUserNicknames.add(message.getSender().getNickname());
            }
        }

        // 나와 주고 받은 유저들 불러오기 성공


        // 유저한테 온 메시지 전부 가져오기
        for (String otherUserNickname: otherUserNicknames){
            RoomDTO roomDTO=new RoomDTO();
            roomDTO.setNickname(otherUserNickname);

            // 최신 메시지를 가져오는 로직
            Message latestMessage = messageRepository.findTopBySenderAndReceiverOrSenderAndReceiverOrderByCreatedAtDesc(
                    user, loginRepository.findByNickname(otherUserNickname),
                    loginRepository.findByNickname(otherUserNickname), user
            );
            // RoomDTO에 맞는 정보 집어넣기
            roomDTO.setId(latestMessage.getMessageRoom().getId());
            roomDTO.setLastMessage(latestMessage.getContent());
            roomDTO.setLastDate(latestMessage.getCreatedAt());
            roomDTOList.add(roomDTO);
        }

        // 반환
        return roomDTOList;
    }

    // 받은 쪽지 조회하는데 쪽지함 id로 조회
    @Transactional
    public List<InboxDTO> messageSearch(Long roomId,LoginDTO userDto){

        Login user=loginRepository.findByNickname(userDto.getNickname());

        MessageRoom room = messageRoomRepository.findById(roomId)
                .orElseThrow(()-> new IllegalArgumentException("쪽지함이 존재하지 않습니다."));

        List<Message> messages = room.getMessages();



        return messages.stream()
                .filter(message -> message.getSender().equals(user) || message.getReceiver().equals(user))
                .map(message -> {
                    InboxDTO dto=InboxDTO.toDto(message);
                    if (message.getSender().equals(user)) {
                        if (message.isDeletedBySender()) {
                            return null; // 보낸 메시지를 논리적으로 삭제한 경우 건너뛰기
                        }
                        dto.setState("보낸 메시지");
                    } else if (message.getReceiver().equals(user)) {
                        if (message.isDeleteByReceiver()) {
                            return null; // 받은 메시지를 논리적으로 삭제한 경우 건너뛰기
                        }
                        dto.setState("받은 메시지");
                    }
                    return dto;

                })
                .collect(Collectors.toList());

    }


    private MessageRoom createOrFindMessageRoom(Login userA, Login userB) {
        // 사용자 ID를 비교하여 정렬
        Login sender = userA.getId() < userB.getId() ? userA : userB;
        Login receiver = userA.getId() < userB.getId() ? userB : userA;

        // 자기 자신에게 쪽지를 보내는 경우 예외 처리
        if (sender.equals(receiver)) {
            throw new IllegalArgumentException("자기 자신에게 쪽지를 보낼 수 없습니다.");
        }

        Optional<MessageRoom> existingMessage = messageRoomRepository.findMessageRoomByUserAAndUserB(sender, receiver);

        if (existingMessage.isPresent()) {
            return existingMessage.get();
        } else {
            MessageRoom messageRoom = MessageRoom.builder()
                    .messages(new ArrayList<>())
                    .userA(sender)
                    .userB(receiver)
                    .build();
            return messageRoomRepository.save(messageRoom);
        }
    }

    @Transactional
    public void deleteMessage(Long messageId, LoginDTO userDto) {
        Message message = messageRepository.findById(messageId)
                .orElseThrow(() -> new IllegalArgumentException("쪽지를 찾을 수 없습니다."));

        Login user = loginRepository.findByNickname(userDto.getNickname());
        if (user.equals(message.getSender()) || user.equals(message.getReceiver())) {
            if (user.equals(message.getSender())) {
                message.deleteBySender();
            }
            if (user.equals(message.getReceiver())) {
                message.deleteByReceiver();
            }

            if (message.isDeleted()) { // 논리적으로 삭제되었다면 실제로 삭제
                messageRepository.delete(message);
            }
        }else {
            throw new IllegalArgumentException("쪽지에 대한 권한이 없습니다.");
        }
    }

    @Transactional
    public void deleteMessageRoom(Long roomId, LoginDTO userDto) {

        Login user = loginRepository.findByNickname(userDto.getNickname());

        MessageRoom room = messageRoomRepository.findById(roomId)
                .orElseThrow(() -> new IllegalArgumentException("쪽지함을 찾을 수 없습니다."));

        if (user.equals(room.getUserA()) || user.equals(room.getUserB())) {
            if (user.equals(room.getUserA())) {
                room.deletedByUserA();
            }
            if (user.equals(room.getUserB())) {
                room.deleteByUserB();
            }

            if (room.isDeleted()) { // 논리적으로 삭제되었다면 실제로 삭제
                messageRoomRepository.delete(room);
            }
        }else {
            throw new IllegalArgumentException("쪽지함에 대한 권한이 없습니다.");
        }


    }
}
