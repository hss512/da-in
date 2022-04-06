package com.dain.repository.chat;

import com.dain.domain.entity.ChatMember;
import com.dain.domain.entity.Room;

import java.util.List;

public interface ChatMemberCustomRepository {

    List<Room> getRoomList(Long memberId);

    List<ChatMember> findAllByMemberId(Long replyMemberId, Long memberId);
}
