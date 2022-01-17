package com.dain.domain.entity;

import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class Scrap extends BaseEntity{

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "scrap_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToOne
    private Board board;
}
