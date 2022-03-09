package com.dain.domain.entity;

import com.dain.domain.dto.MemberDTO;
import com.dain.domain.dto.ReplyDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Reply extends BaseEntity{

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reply_id")
    private Long id;

    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board;

    public ReplyDTO toReplyDTO(){
        return ReplyDTO.builder()
                .id(this.id)
                .boardDTO(this.board.toBoardDTO())
                .memberDTO(MemberDTO.ResponseMemberDTO.builder()
                        .id(this.member.getId())
                        .username(this.member.getUsername())
                        .nickname(this.member.getNickname())
                        .local(this.member.getLocal())
                        .age(this.member.getAge())
                        .gender(this.member.getGender())
                        .build())
                .content(this.content)
                .build();
    }
}
