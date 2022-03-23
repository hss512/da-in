package com.dain.chat;


import com.dain.domain.entity.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class ChatMessage {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "message_id")
    private Long id;


    private LocalDateTime chatTime;

    @ManyToOne
    @JoinColumn(name = "room_id")
    private ChatRoom chatRoom;

    /*@ManyToOne
    @JoinColumn(name = "member_id")*/
    private String writer;

    private String message;

    @Builder
    public ChatMessage(Long id, LocalDateTime chatTime, ChatRoom chatRoom, String  writer, String message) {
        this.id = id;
        this.chatTime = chatTime;
        this.chatRoom = chatRoom;
        this.writer = writer;
        this.message = message;
    }

    public ChatMessageDto toDto(){
        return ChatMessageDto.builder()
                .writer(writer)
                .message(message)
                .build();
    }
}
