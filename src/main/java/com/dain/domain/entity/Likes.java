package com.dain.domain.entity;

import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
@Table(
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "like_uk",
                        columnNames = {"board_id","member_id"}
                )
        }
)
public class Like {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "like_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;
}