package com.dain.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResponseAlarmDTO {

    private Long alarmId;
    private MemberDTO.ResponseMemberDTO member;
    private String message;
    private Long boardId;
    private int check;

}
