package com.dain.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@Builder
public class Alarm extends BaseEntity{

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "alarm_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    private String message;

    public Alarm(Long id, Member toMember, String message){
        this.id = id;
        this.member = toMember;
        this.message = message;

        toMember.addAlarm(this);
    }

    public Alarm(Member toMember, String message){
        this.member = toMember;
        this.message = message;

        toMember.addAlarm(this);
    }
}
