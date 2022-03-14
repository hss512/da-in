package com.dain.chat;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatMessage {

    private String chatRoomId;
    private MessageType type;
    private String message;
    private String writer;

}
