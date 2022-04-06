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

import java.util.*;
import java.util.stream.Collectors;

import static java.lang.Long.parseLong;

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

        log.info("roomTitle={}",room.getTitle());

        return room.toDTO();
    }

    @Transactional(readOnly = true)
    public List<RoomDTO> getRoomList(Long memberId) {

        List<Room> roomList = chatMemberRepository.getRoomList(memberId);

        List<RoomDTO> result = roomList.stream().map(Room::toDTO).sorted(Comparator.comparing(RoomDTO::getId)).collect(Collectors.toList());

        List<ChatMember> chatMemberList = new ArrayList<>();

        for (RoomDTO roomDTO : result) {
            List<ChatMember> byRoomId = chatMemberRepository.findByRoomId(roomDTO.getId());
            List<ChatMember> collect = byRoomId.stream().filter(chatMember -> chatMember.getMember().getId() != memberId).collect(Collectors.toList());
            chatMemberList.add(collect.get(0));
        }

        for (int i = 0; i < result.size(); i++) {
            int count = Integer.parseInt(chatRepository.countByRoomIdAndMemberIdAndChatCheckIsFalse(chatMemberList.get(i).getRoom().getId(), chatMemberList.get(i).getMember().getId()).toString());
            result.get(i).setCount(count);
        }

        return result;
    }

    public int checkChatRoom(Long replyMemberId, Long memberId) {

        int count = 0;

        log.info("checkChatRoom_replyMemberId={}", replyMemberId);
        log.info("checkChatRoom_memberId={}", memberId);

        Member replyMember = memberRepository.findById(replyMemberId).get();
        Member member = memberRepository.findById(memberId).get();

        StringBuilder sb1 = new StringBuilder();
        sb1.append(replyMember.getNickname()).append("-").append(member.getNickname()).append("-");
        StringBuilder sb2 = new StringBuilder();
        sb2.append(member.getNickname()).append("-").append(replyMember.getNickname()).append("-");

        if(roomRepository.findByTitle(sb1.toString()) != null || roomRepository.findByTitle(sb2.toString()) != null){
            count++;
        }

        if(count == 1){
            return 1;
        }else {
            return 0;
        }
    }

    public void exitRoom(String roomId, Long memberId) {
        List<ChatMember> byRoomId = chatMemberRepository.findByRoomId(parseLong(roomId));
        chatMemberRepository.deleteByRoomIdAndMemberId(parseLong(roomId), memberId);
        if(byRoomId.size() == 1){
            chatRepository.deleteAllByRoomId(parseLong(roomId));
            roomRepository.deleteById(parseLong(roomId));
        }
    }

    @Transactional(readOnly = true)
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

    @Transactional(readOnly = true)
    public List<ChatDTO> getChatList(Long roomId) {

        List<Chat> chatList = chatRepository.findByRoomId(roomId);

        List<ChatDTO> chatDTOList = chatList.stream().map(Chat::toDTO).collect(Collectors.toList());

        return chatDTOList;
    }

    public void readChat(Long roomId, String username) {

        Member member = memberRepository.findByUsername(username).get();

        List<Chat> getChatList = chatRepository.findByRoomIdAndMemberIdAndChatCheckIsFalse(roomId, member.getId());

        for (Chat chat : getChatList) {
            chat.chatRead();
        }

        log.info("readChat-username={}", username);
    }

    public void readAnotherChat(Long roomId, Long memberId) {
        List<ChatMember> getChatMember = chatMemberRepository.findByRoomId(roomId);
        List<ChatMember> anotherMember = getChatMember.stream().filter(m -> m.getMember().getId() != memberId).collect(Collectors.toList());
        for (ChatMember chatMember : anotherMember) {
            log.info("filter_member={}", chatMember.getMember().getId());
        }
        String username = anotherMember.get(0).getMember().getUsername();

        readChat(roomId, username);
    }

    public void readRealTime(Long chatId) {
        Chat chat = chatRepository.findById(chatId).get();
        chat.chatRead();
        log.info("채팅 읽음={}", chatRepository.findById(chatId).get().isChatCheck());
    }

    @Transactional(readOnly = true)
    public int getChatCheck(Long chatId) {

        boolean chatCheck = chatRepository.findById(chatId).get().isChatCheck();

        log.info("메세지 append시킴");

        if(chatCheck){
            return 0;
        }else{
            return 1;
        }
    }

    @Transactional(readOnly = true)
    public int getMemberChatList(String username, String roomId) {
        Long count = 0L;
        List<ChatMember> byRoomId = chatMemberRepository.findByRoomId(parseLong(roomId));
        List<ChatMember> result = byRoomId.stream().filter(chatMember -> !chatMember.getMember().getUsername().equals(username)).collect(Collectors.toList());
        Long memberId = result.get(0).getMember().getId();
        return chatMember_findByMemberId(count, memberId);
    }

    private int chatMember_findByMemberId(Long count, Long memberId) {
        List<ChatMember> byMemberId = chatMemberRepository.findByMemberId(memberId);
        for (ChatMember chatMember : byMemberId) {
            Long anotherMemberRoomId = chatMember.getRoom().getId();
            List<ChatMember> resultChatMember = chatMemberRepository.findByRoomId(anotherMemberRoomId);
            List<ChatMember> resultList = resultChatMember.stream().filter(cm -> !cm.getMember().getId().equals(memberId)).collect(Collectors.toList());
            for (ChatMember member : resultList) {
                count += chatRepository.countByRoomIdAndMemberIdAndChatCheckIsFalse(member.getRoom().getId(), member.getMember().getId());
            }
        }

        return Integer.parseInt(count.toString());
    }

    public Long anotherMember(String username, String roomId) {
        List<ChatMember> byRoomId = chatMemberRepository.findByRoomId(parseLong(roomId));
        List<ChatMember> result = byRoomId.stream().filter(chatMember -> !chatMember.getMember().getUsername().equals(username)).collect(Collectors.toList());

        return result.get(0).getMember().getId();
    }

    public int chatAllAlarm(Long memberId) {
        Long count = 0L;
        return chatMember_findByMemberId(count, memberId);
    }
}
