package com.dain.chat;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import javax.persistence.*;
import java.io.IOException;
import java.util.*;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class ChatRoom {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "room_id")
    private Long id;
    private String roomCode;
    private String name;

    @OneToMany(mappedBy = "chatRoom",cascade = CascadeType.ALL)
    private List<ChatMessage> messages=new ArrayList<>();

    private int userLimit;
    @Builder
    public ChatRoom(Long id,String name, String roomCode,int userLimit) {
        this.id = id;
        this.name=name;
        this.roomCode = roomCode;
        this.userLimit=userLimit;
    }

    /*
    private Set<WebSocketSession> sessions = new HashSet<>();
*/



    /*public static ChatRoom create(String name,int userLimit){
        ChatRoom chatRoom = new ChatRoom();
        chatRoom.roomId = UUID.randomUUID().toString();
        chatRoom.name = name;
        chatRoom.userLimit=userLimit;
        return chatRoom;
    }*/

    /*public void handleMessage(WebSocketSession session, ChatMessage chatMessage,
                              ObjectMapper objectMapper) throws IOException {
        if(chatMessage.getType() == MessageType.ENTER){
            sessions.add(session);
            chatMessage.setMessage(chatMessage.getWriter() + "님이 입장하셨습니다.");
        }
        else if(chatMessage.getType() == MessageType.LEAVE){
            sessions.remove(session);
            chatMessage.setMessage(chatMessage.getWriter() + "님임 퇴장하셨습니다.");
        }
        else{
            chatMessage.setMessage(chatMessage.getWriter() + " : " + chatMessage.getMessage());
        }
        send(chatMessage,objectMapper);
    }

    private void send(ChatMessage chatMessage, ObjectMapper objectMapper) throws IOException {
        TextMessage textMessage = new TextMessage(objectMapper.
                writeValueAsString(chatMessage.getMessage()));
        for(WebSocketSession sess : sessions){
            sess.sendMessage(textMessage);
        }
    }*/
}