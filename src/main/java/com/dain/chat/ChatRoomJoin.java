package com.dain.chat;

import com.dain.domain.entity.Member;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class ChatRoomJoin {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "room_id")
    private ChatRoom chatRoom;

    private int dropUserRoom;

    @Column(name = "room_owner")
    @Enumerated(EnumType.STRING)
    private RoomOwner roomOwner;

    @Column(name = "chat_state")
    @Enumerated(EnumType.STRING)
    private MessageType messageType;

    public void toUpdateDropUserRoom(int dropUserRoom){
        this.dropUserRoom=dropUserRoom;
    }
}
