package com.dain.domain.dto;

import com.dain.domain.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BoardMemberDTO {

    private Long id;

    private String username;

    private String nickname;

}
