package com.dain.chat;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter@Setter
@NoArgsConstructor
public class ChatRoomForm {
    private Long id;
    private int userLimit;
    private String roomCode;
    private String name;
    public ChatRoom toEntity(){
        return ChatRoom.builder()
                .id(id)
                .name(name)
                .userLimit(userLimit)
                .roomCode(roomCode)
                .build();
    }
}
