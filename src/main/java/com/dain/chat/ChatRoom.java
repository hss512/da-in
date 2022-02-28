package com.dain.chat;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Data
public class ChatRoom {
    private String roomId;
    private String name;
    private Set<WebSocketSession> sessions=new HashSet<>();

    public static ChatRoom create(String name){
        ChatRoom chatRoom = new ChatRoom();
        chatRoom.roomId= UUID.randomUUID().toString();
        chatRoom.name=name;
        return chatRoom;
    }

    public void handleMessage(WebSocketSession session, ChatMessage chatMessage, ObjectMapper objectMapper) throws IOException {
        if(chatMessage.getMessageType()==MessageType.ENTER){
            sessions.add(session);
            chatMessage.setMessage(chatMessage.getWriter()+"님이 입장하셨습니다.");
        }else if(chatMessage.getMessageType()==MessageType.LEAVE){
            sessions.remove(session);
            chatMessage.setMessage(chatMessage.getWriter()+"님이 떠나셨습니다.");
        }else{
            chatMessage.setMessage(chatMessage.getWriter()+" : "+chatMessage.getMessage());
        }
        send(chatMessage,objectMapper);
    }

    public void send(ChatMessage chatMessage,ObjectMapper objectMapper) throws IOException{
        TextMessage textMessage = new TextMessage(objectMapper.writeValueAsString(chatMessage.getMessage()));

        for (WebSocketSession session:sessions){
            session.sendMessage(textMessage);
        }
    }
}
