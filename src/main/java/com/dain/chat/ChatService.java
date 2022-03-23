package com.dain.chat;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;


@RequiredArgsConstructor
@Service
@Log4j2
@Transactional(readOnly = true)
public class ChatService {

    private final ChatRoomRepository chatRoomRepository;

    private final ChatRepository chatRepository;

    public List<ChatRoom> findAllRoom(){
        List<ChatRoom> chatRoomList = chatRoomRepository.findAll();
        Collections.reverse(chatRoomList);
        return chatRoomList;
    }

    public ChatRoom findRoom(String roomCode){
        ChatRoom chatRoom = chatRoomRepository.findByRoomCode(roomCode).get();
        return chatRoom;
    }

    public Long createChatRoom(ChatRoomForm dto){
        dto.setRoomCode(UUID.randomUUID().toString());
        return chatRoomRepository.save(dto.toEntity()).getId();
    }

    public Long saveChat(ChatMessage chatMessage){
        chatMessage.setChatTime(LocalDateTime.now());
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



}


