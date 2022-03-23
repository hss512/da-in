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

    private LocalDateTime chatTime;

    private ChatRoom chatRoom;

    private String writer;

    private String message;

    @Builder
    public ChatMessageDto(String writer, String message) {
        this.writer = writer;
        this.message = message;
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
