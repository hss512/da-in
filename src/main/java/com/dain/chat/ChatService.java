package com.dain.chat;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.*;


@RequiredArgsConstructor
@Service
public class ChatService {

    private final ChatRoomRepository chatRoomRepository;


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


}


