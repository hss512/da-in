package com.dain.domain.entity;

import com.dain.domain.dto.RoomDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Room extends BaseEntity{

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String roomCode;

    private String title;

    private int enter;

    public RoomDTO toDTO(){
        return RoomDTO.builder()
                .id(this.id)
                .roomCode(this.roomCode)
                .title(this.title)
                .build();
    }
}
