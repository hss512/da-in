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

    private String imagePath;

    private String role;

    private String email;

    @Builder
    public MemberDto(Long id, String username, String nickname, String password, String local, int age, String gender,  String imagePath, String role,String email){
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

    public Member toEntity(){
        return Member.builder()
                .id(id)
                .email(email)
                .username(username)
                .nickname(nickname)
                .password(password)
                .local(local)
                .age(age)
                .gender(gender)
                .imagePath(imagePath)
                .role(role)
                .build();
        //커밋용 주석

    }
}
