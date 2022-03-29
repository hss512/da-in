package com.dain.service;

import com.dain.domain.dto.ChatDTO;
import com.dain.domain.dto.RoomDTO;
import com.dain.domain.entity.Chat;
import com.dain.domain.entity.ChatMember;
import com.dain.domain.entity.Member;
import com.dain.domain.entity.Room;
import com.dain.repository.chat.ChatMemberRepository;
import com.dain.repository.MemberRepository;
import com.dain.repository.chat.ChatRepository;
import com.dain.repository.chat.RoomRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
@Log4j2
public class ChatService {

    private final RoomRepository roomRepository;
    private final MemberRepository memberRepository;
    private final ChatMemberRepository chatMemberRepository;
    private final ChatRepository chatRepository;

    public RoomDTO createChatRoom(Long toMemberId, Long fromMemberId) {

        StringBuilder sb = new StringBuilder();

        List<Long> ids = new ArrayList<>(Arrays.asList(toMemberId, fromMemberId));

        List<Member> memberList = memberRepository.findAllById(ids);

        for (Member member : memberList) {
            sb.append(member.getNickname()).append("-");
        }

        Room createRoom = Room.builder()
                .title(sb.toString())
                .enter(0)
                .roomCode(String.valueOf(UUID.randomUUID()))
                .build();

        Room room = roomRepository.save(createRoom);

        for (Member member : memberList) {
            ChatMember createChatMember = ChatMember.builder()
                    .member(member)
                    .room(room)
                    .build();

            chatMemberRepository.save(createChatMember);
        }

        return room.toDTO();
    }

    public List<RoomDTO> getRoomList(Long memberId) {

        List<Room> roomList = chatMemberRepository.getRoomList(memberId);

        List<RoomDTO> result = roomList.stream().map(Room::toDTO).collect(Collectors.toList());

        return result;
    }

    public int checkChatRoom(Long replyMemberId, Long memberId) {

        StringBuilder sb = new StringBuilder();

        List<ChatMember> memberList = chatMemberRepository.findAllByMemberId(replyMemberId, memberId);

        for (ChatMember chatMember : memberList) {
            sb.append(chatMember.getRoom().getId());
        }

        String s = sb.toString();

        for (ChatMember chatMember : memberList) {
            if(s.contains(String.valueOf(chatMember.getRoom().getId()))){
                return 1;
            }
        }

        return 0;
    }

    public void exitRoom(String roomId, Long memberId) {
        chatMemberRepository.deleteByRoomIdAndMemberId(Long.parseLong(roomId), memberId);
    }

    public RoomDTO getRoom(Long roomId, Long memberId) {
        RoomDTO roomDTO = roomRepository.findById(roomId).get().toDTO();
        Member member = memberRepository.findById(memberId).get();
        roomDTO.setMyNickname(member.getNickname());
        return roomDTO;
    }

    public ChatDTO createChat(Long roomId, String username, String message) {
        Room room = roomRepository.findById(roomId).get();
        Member member = memberRepository.findByUsername(username).get();
        Chat chat = Chat.builder()
                .member(member)
                .room(room)
                .content(message)
                .chatCheck(false)
                .build();

        return chatRepository.save(chat).toDTO();
    }

    public List<ChatDTO> getChatList(Long roomId) {

        List<Chat> chatList = chatRepository.findByRoomId(roomId);

        List<ChatDTO> chatDTOList = chatList.stream().map(Chat::toDTO).collect(Collectors.toList());

        return chatDTOList;
    }

    public void readChat(Long roomId) {
        chatRepository.findByIdAndChatCheckIsFalse(roomId);
    }
}
