package com.dain.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoomDTO {

    private Long id;
    private String roomCode;
    private String title;
    private String myNickname;
    private int count;

    private List<ChatDTO> chatList;
}
