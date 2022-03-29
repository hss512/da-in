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

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "room_id")
    private ChatRoom chatRoom;

    private int dropUserRoom;

    @Column(name = "room_owner")
    @Enumerated(EnumType.STRING)
    private RoomOwner roomOwner;

    public void toInsertRoomState(ChatRoomForm chatRoomForm){
        this.chatRoom=chatRoomForm.toEntity();
    }
}
