package com.dain.domain.entity;

import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class Image extends BaseEntity{

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "image_id")
    private Long id;

    private String imagePath;

    @ManyToOne
    @JoinColumn(name = "board_id")
    private Board board;
}
