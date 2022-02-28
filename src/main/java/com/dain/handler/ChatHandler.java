package com.dain.handler;

import com.dain.chat.ChatMessage;
import com.dain.chat.ChatRoom;
import com.dain.chat.ChatRoomRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.ArrayList;
import java.util.List;

@Component
@Log4j2
@RequiredArgsConstructor
public class ChatHandler extends TextWebSocketHandler {

    private final ChatRoomRepository chatRoomRepository;
    private final ObjectMapper objectMapper;

    //private static List<WebSocketSession> sessionList=new ArrayList<>();

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String msg=message.getPayload();//payload가 JSON형태로 들어온다
        log.info("msg={} : {}",session,msg);
        /*for (WebSocketSession sessions:sessionList){
            TextMessage msg=new TextMessage(message.getPayload());
            sessions.sendMessage(msg);
        }*/
        ChatMessage chatMessage = objectMapper.readValue(msg, ChatMessage.class);//이를 우리가 미리 사전에 작성한 ChatMessage 클래스에 역직렬화를 통해 넣어준다.\
        ChatRoom chatroom = chatRoomRepository.findRoomById(chatMessage.getChatRoomId());
        chatroom.handleMessage(session,chatMessage,objectMapper);
    }

    /*@Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        sessionList.add(session);
        log.info("session에 클라이언트입장={}",session);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        log.info("session에서 클라이언트퇴장={}",session);
        sessionList.remove(session);
    }*/
}
