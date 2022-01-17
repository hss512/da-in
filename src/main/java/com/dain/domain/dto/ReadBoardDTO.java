package com.dain.domain.dto;

import com.dain.domain.entity.Category;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReadBoardDTO {

    private Long id;

    private String title;

    private String content;

    private String local;

    private int age;

    private String gender;

    private BoardMemberDTO member;

    private Category category;
}
