package com.dain.domain.entity;

import com.dain.domain.dto.BoardMemberDTO;
import com.dain.domain.dto.MemberDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Member extends BaseEntity{

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_username",nullable = false,unique = true)
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

    @Column(name = "user_email",unique = true)
    private String email;

    public MemberDTO toDto(){
        return MemberDTO.builder()
                .id(id)
                .username(username)
                .nickname(nickname)
                .password(password)
                .local(local)
                .age(age)
                .gender(gender)
                .imagePath(imagePath)
                .role(role)
                .email(email)
                .build();
    }

    public MemberDTO.ResponseMemberDTO toResponseDTO(){
        return MemberDTO.ResponseMemberDTO.builder()
                .id(id)
                .username(username)
                .nickname(nickname)
                .local(local)
                .age(age)
                .gender(gender)
                .build();
    }


    public BoardMemberDTO toBoardMemberDTO(){
        return BoardMemberDTO.builder()
                .id(this.getId())
                .username(this.getUsername())
                .nickname(this.getNickname())
                .build();
    }

    public void toUpdateMember(String nickname,String local){
        this.nickname=nickname;
        this.local=local;
    }
}