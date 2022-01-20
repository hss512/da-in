package com.dain.domain.entity;

import com.dain.domain.dto.BoardMemberDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Member extends BaseEntity{

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    private String username;

    private String nickname;

    private String password;

    private String local;

    private int age;

    private String gender;

    private String imagePath;

    private String role;

    private String email;

    public BoardMemberDTO toBoardMemberDTO(){
        return BoardMemberDTO.builder()
                .id(this.getId())
                .username(this.getUsername())
                .nickname(this.getNickname())
                .build();
    }
}
