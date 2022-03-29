package com.dain.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChatDTO {

    private Long id;

    private String message;

    private int chatCheck;

    private MemberDTO.ResponseMemberDTO memberDTO;

    private RoomDTO roomDTO;

    private LocalDateTime created_at;
}
