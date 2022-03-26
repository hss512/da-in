package com.dain.domain.entity;

import com.dain.domain.dto.ChatDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class Chat extends BaseEntity{

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chat_id")
    private Long id;

    private String content;

    private int chatCheck;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "room_id")
    private Room room;

    public ChatDTO toDTO(){
        return ChatDTO.builder()
                .id(this.id)
                .message(this.content)
                .memberDTO(this.member.toResponseDTO())
                .roomDTO(this.room.toDTO())
                .build();
    }
}
