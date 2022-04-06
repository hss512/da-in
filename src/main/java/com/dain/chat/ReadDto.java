package com.dain.chat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ReadDto {
    private String messageId;
    private String userId;
    private String roomCode;
    private int chatUserCount;
}
