package com.dain.chat;

import com.dain.domain.entity.Member;
import com.dain.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;


@RequiredArgsConstructor
@Service
@Log4j2
@Transactional(readOnly = true)
public class ChatService {

    private final ChatRoomRepository chatRoomRepository;

    private final ChatRepository chatRepository;

    private final ChatRoomJoinRepository chatRoomJoinRepository;

    private final MemberRepository memberRepository;

    public List<ChatRoom> findAllRoom(){
        List<ChatRoom> chatRoomList = chatRoomRepository.findAll();
        Collections.reverse(chatRoomList);
        return chatRoomList;
    }

    public ChatRoom findRoom(String roomCode){
        ChatRoom chatRoom = chatRoomRepository.findByRoomCode(roomCode).get();
        return chatRoom;
    }

    public Long createChatRoom(ChatRoom chatRoom){
        chatRoom.setRoomCode(UUID.randomUUID().toString());
        return chatRoomRepository.save(chatRoom).getId();
    }

    public Long saveChat(ChatMessage chatMessage){
        return chatRepository.save(chatMessage).getId();
    }

    @Transactional
    public ResponseEntity<?> userCountCheck(int countUser,int userLimit,Long id){
        ChatRoom findRoom = chatRoomRepository.findById(id).get();
        log.info("roomid={}",id);
        log.info("roomCU={}",countUser);
        log.info("roomUL={}",userLimit);
        if (findRoom.getId()==id){
            if (findRoom.getUserLimit()>0&& countUser<findRoom.getUserLimit()){
                findRoom.setCountUser(countUser+1);
                return new ResponseEntity<>(0,HttpStatus.OK);
            }else if(findRoom.getCountUser()== findRoom.getUserLimit()){
                return new ResponseEntity<>(1,HttpStatus.OK);
            }else {
                return new ResponseEntity<>(1,HttpStatus.OK);
            }
        }else {
            return new ResponseEntity<>(123123,HttpStatus.BAD_REQUEST);
        }
    }

    public Long saveChatRoomJoinOwner(Member member,ChatRoom chatRoom){
        ChatRoomJoin chatRoomJoin=new ChatRoomJoin();
        chatRoomJoin.setMember(member);
        chatRoomJoin.setChatRoom(chatRoom);
        chatRoomJoin.setRoomOwner(RoomOwner.OWNER);
        return chatRoomJoinRepository.save(chatRoomJoin).getId();
    }

    public Long saveChatRoomJoinGuest(Member member,ChatRoom chatRoom){
        ChatRoomJoin chatRoomJoin=new ChatRoomJoin();
        chatRoomJoin.setMember(member);
        chatRoomJoin.setChatRoom(chatRoom);
        chatRoomJoin.setRoomOwner(RoomOwner.GUEST);
        return chatRoomJoinRepository.save(chatRoomJoin).getId();
    }

    public boolean ifExistSaveRoomJoin(Long memberId,Long roomId){
        Member findMember = memberRepository.findById(memberId).get();
        ChatRoom chatRoom = chatRoomRepository.findById(roomId).get();
        Optional<ChatRoomJoin> findChatRoomJoin = chatRoomJoinRepository.findByChatRoomAndMember(chatRoom, findMember);
        if(findChatRoomJoin.isEmpty()){
            return true;
        }else {
            return false;
        }
    }

    public boolean ifExistSaveEnter(String writer,ChatRoom chatRoom){
        Optional<ChatMessage> findChatMessage = chatRepository.findByWriterAndChatRoom(writer, chatRoom);
        if(findChatMessage.isEmpty()){
            return true;
        }else {
            return false;
        }
    }

    @Transactional
    public ResponseEntity<?> userCountMinus(String roomCode){
        ChatRoom chatRoom = chatRoomRepository.findByRoomCode(roomCode).get();
        if (chatRoom.getCountUser()!=0 &&chatRoom.getCountUser()>0){
            chatRoom.toUpdateCountUser(chatRoom.getCountUser()-1);
            return new ResponseEntity<>(1,HttpStatus.OK);
        }else {
            return new ResponseEntity<>(0,HttpStatus.OK);
        }
    }

    public boolean userInChatRoom(ChatRoom chatRoom,Member member){
        log.info("여기 매번들어오는곳인데 뭔개소리야시발좇같게진짜");
        Optional<ChatRoomJoin> chatRoomJoin = chatRoomJoinRepository.findByChatRoomAndMember(chatRoom, member);
        if(chatRoomJoin.isPresent()){
            return true;
        }else {
            return false;
        }
    }
}


