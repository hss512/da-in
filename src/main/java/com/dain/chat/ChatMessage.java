package com.dain.chat;

import lombok.Data;

@Data
public class ChatMessage {
    private String chatRoomId;
    private String writer;
    private String message;
    private MessageType messageType;
}
