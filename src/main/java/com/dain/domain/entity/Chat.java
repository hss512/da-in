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

    private boolean chatCheck;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "room_id")
    private Room room;

    public ChatDTO toDTO(){

        if(this.chatCheck) {
            return ChatDTO.builder()
                    .id(this.id)
                    .chatCheck(1)
                    .message(this.content)
                    .memberDTO(this.member.toResponseDTO())
                    .roomDTO(this.room.toDTO())
                    .created_at(this.createdDate)
                    .build();
        }else{
            return ChatDTO.builder()
                    .id(this.id)
                    .chatCheck(0)
                    .message(this.content)
                    .memberDTO(this.member.toResponseDTO())
                    .roomDTO(this.room.toDTO())
                    .created_at(this.createdDate)
                    .build();
        }
    }
}
