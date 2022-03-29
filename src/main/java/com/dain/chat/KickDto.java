package com.dain.chat;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class KickDto {
    private String roomId;
    private Long userId;
    private String type;
}
