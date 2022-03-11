package com.dain.chat;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Chat {

    @Id @GeneratedValue
    @Column(name = "chat_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id")
    private Room room;

    private String sender;

    @Column(columnDefinition = "TEXT")
    private String message;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime sendDate;


    @Builder
    public Chat(Room room,String sender,String message){
        this.room=room;
        this.sender=sender;
        this.message=message;
        this.sendDate=LocalDateTime.now();
    }

    public static Chat createChat(Room room,String sender,String message){
        return Chat.builder()
                .room(room)
                .sender(sender)
                .message(message)
                .build();
    }
}
