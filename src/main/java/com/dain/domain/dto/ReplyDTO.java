package com.dain.domain.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReplyDTO {

    private Long id;

    private MemberDTO.ResponseMemberDTO memberDTO;

    private BoardDTO boardDTO;

    private String content;

}
