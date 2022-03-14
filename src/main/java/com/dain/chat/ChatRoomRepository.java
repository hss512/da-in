package com.dain.chat;

import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.*;

@Repository
public class ChatRoomRepository {
    private Map<String,ChatRoom> chatRoomMap;

    @PostConstruct
    private void init(){
        chatRoomMap=new LinkedHashMap<>();
    }

    public List<ChatRoom> findAllRooms(){
        List<ChatRoom> chatRooms = new ArrayList<>(chatRoomMap.values());
        Collections.reverse(chatRooms);
        return chatRooms;
    }

    public ChatRoom findRoomById(String id){
        return chatRoomMap.get(id);
    }

    public ChatRoom createChatRoom(String name){
        ChatRoom chatRoom = ChatRoom.create(name);
        chatRoomMap.put(chatRoom.getRoomId(),chatRoom);
        return chatRoom;
    }

}
