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
    @JoinColumn(name = "from_member_id")
    private Member fromMember;

    @ManyToOne
    @JoinColumn(name = "to_member_id")
    private Member toMember;

    private String message;

    public Alarm(Long id, Member fromMember, Member toMember, String message){
        this.id = id;
        this.fromMember = fromMember;
        this.toMember = toMember;
        this.message = message;

        toMember.addAlarm(this);
    }

    public Alarm(Member fromMember, Member toMember, String message){
        this.fromMember = fromMember;
        this.toMember = toMember;
        this.message = message;

        toMember.addAlarm(this);
    }
}
