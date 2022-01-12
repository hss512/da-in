package com.dain.domain.entity;

import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class Category extends BaseEntity{

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Long id;

    private String title;
}
