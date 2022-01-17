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

    private String phone;

    private String imagePath;

    private String role;

    @Builder
    public Member(Long id, String username, String nickname, String password, String local, int age, String gender, String phone, String imagePath, String role){
        this.id=id;
        this.username=username;
        this.nickname=nickname;
        this.password=password;
        this.local=local;
        this.age=age;
        this.gender = gender;
        this.phone=phone;
        this.imagePath=imagePath;
        this.role=role;
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
                .phone(phone)
                .imagePath(imagePath)
                .role(role)
                .build();
    }


    public Member() {

    }
}