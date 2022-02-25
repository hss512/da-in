package com.dain.domain.entity;

import com.dain.domain.dto.BoardMemberDTO;
import com.dain.domain.dto.MemberDto;
import lombok.AllArgsConstructor;
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


    @Builder
    public Member(Long id, String username, String nickname, String password, String local, int age, String gender,  String imagePath, String role,String email){
        this.id=id;
        this.username=username;
        this.nickname=nickname;
        this.password=password;
        this.local=local;
        this.age=age;
        this.gender = gender;
        this.imagePath=imagePath;
        this.role=role;
        this.email=email;
    }

    public MemberDto toDto(){
        return MemberDto.builder()
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