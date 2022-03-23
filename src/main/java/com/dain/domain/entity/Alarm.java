package com.dain.domain.entity;

import com.dain.domain.dto.MemberDTO;
import com.dain.domain.dto.ResponseAlarmDTO;
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
public class Alarm extends BaseEntity{

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "alarm_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    private String message;

    private int checkAlarm;

    private int removeAlarm;

    private Long boardId;

    public Alarm(Long id, Member toMember, String message, Long boardId){
        this.id = id;
        this.boardId = boardId;
        this.member = toMember;
        this.message = message;
    }

    public Alarm(Member toMember, String message, Long boardId){
        this.member = toMember;
        this.message = message;
        this.boardId = boardId;
    }

    public ResponseAlarmDTO toAlarmDTO(){
        return ResponseAlarmDTO.builder()
                .alarmId(this.id)
                .member(MemberDTO.ResponseMemberDTO.builder()
                        .id(this.member.getId())
                        .username(this.member.getUsername())
                        .nickname(this.member.getNickname())
                        .local(this.member.getLocal())
                        .age(this.member.getAge())
                        .gender(this.member.getGender())
                        .build())
                .boardId(this.boardId)
                .check(this.checkAlarm)
                .message(this.message)
                .build();
    }

    public void checkUpdate(){
        this.checkAlarm = 1;
    }
}
