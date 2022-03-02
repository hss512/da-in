package com.dain.domain.entity;

import com.dain.domain.dto.ReadBoardDTO;
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
public class Board extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_id")
    private Long id;

    private String title;

    private String content;

    private String local;

    private int ltAge;

    private int rtAge;

    private String gender;

    private int likeCount = 0;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    public void addMember(Member member) {
        this.member = member;
    }

    public void update(String title, String content, String local, int ageLt, int ageRt, String gender, Member member, Category category) {
        this.title = title;
        this.content = content;
        this.local = local;
        this.ltAge = ageLt;
        this.rtAge = ageRt;
        this.gender = gender;
        this.member = member;
        this.category = category;
    }

    public void addCategory(Category category) {
        this.category = category;
    }

    public ReadBoardDTO toReadBoardDTO(){
        return ReadBoardDTO.builder()
                .id(this.id)
                .title(this.title)
                .content(this.content)
                .local(this.local)
                .ageLt(this.ltAge)
                .ageRt(this.rtAge)
                .gender(this.gender)
                .member(this.member.toBoardMemberDTO())
                .category(this.category)
                .build();
    }
}
