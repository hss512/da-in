package com.dain.domain.dto;

import lombok.Data;

@Data
public class Message {
    public enum MessageType{
        ENTER,COMM
    }
    private MessageType messageType;
    private String roomId;
    private String sender;
    private String message;
}
