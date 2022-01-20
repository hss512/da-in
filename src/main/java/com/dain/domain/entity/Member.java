package com.dain.domain.entity;

import com.dain.domain.dto.BoardMemberDTO;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@Builder
@NoArgsConstructor
public class Member extends BaseEntity{

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Column(name = "user_email",nullable = false)
    private String username;

    @Column(name = "user_nickname",nullable = false,unique = true)
    private String nickname;

    @Column(name = "user_password",nullable = false)
    private String password;

    private String local;

    private int age;

    private String gender;

    private String imagePath;

    private String role;

    public BoardMemberDTO toBoardMemberDTO(){
        return BoardMemberDTO.builder()
                .id(this.getId())
                .username(this.getUsername())
                .nickname(this.getNickname())
                .build();
    }
}