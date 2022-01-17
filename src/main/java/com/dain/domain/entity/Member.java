package com.dain.domain.entity;

import com.dain.domain.dto.MemberDto;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
@Builder
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

    //커밋용 주석

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


    public Member() {

    }
}