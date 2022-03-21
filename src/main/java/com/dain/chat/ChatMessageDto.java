package com.dain.chat;

import com.dain.domain.entity.Member;
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

    public ChatMessage toEntity(){
        return ChatMessage.builder()
                .id(id)
                .chatTime(chatTime)
                .chatRoom(chatRoom)
                .message(message)
                .build();
    }
}
