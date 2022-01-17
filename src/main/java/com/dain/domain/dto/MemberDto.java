package com.dain.domain.dto;

import com.dain.domain.entity.Member;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MemberDto {

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
    public MemberDto(Long id, String username, String nickname, String password, String local, int age, String gender, String phone, String imagePath, String role){
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

    public Member toEntity(){
        return Member.builder()
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
}
