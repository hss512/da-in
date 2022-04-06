package com.dain.chat;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
@NoArgsConstructor
public class ChatMessageDto {

    private Long id;

    private String chatTime;

    private ChatRoom chatRoom;

    private String writer;

    private String message;

    private MessageType messageType;

    private int chatRoomUserCount;

    @Builder
    public ChatMessageDto(Long id,String writer, String message, String chatTime,ChatRoom chatRoom,MessageType messageType,int chatRoomUserCount){
        this.id=id;
        this.writer=writer;
        this.message=message;
        this.chatTime=chatTime;
        this.chatRoomUserCount=chatRoomUserCount;
        this.messageType=messageType;
    }

    public ChatMessage toEntity(){
        return ChatMessage.builder()
                .id(id)
                .chatTime(chatTime)
                .chatRoom(chatRoom)
                .message(message)
                .build();
    }
}
