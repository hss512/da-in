package com.dain.chat;

import com.dain.domain.entity.Member;
import com.dain.principal.UserDetailsImpl;
import com.dain.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

@Log4j2
@Controller
@RequiredArgsConstructor
public class ChatRoomController {
    private final ChatRoomRepository chatRoomRepository;

    private final ChatService chatService;

    private final MemberService memberService;

    @GetMapping("/chat/rooms")
    public String rooms(Model model){
        model.addAttribute("rooms",chatService.findAllRoom());
        return "/chat/rooms";
    }

    @GetMapping("/chat/room")
    public String getRoom(String roomCode, Model model, @AuthenticationPrincipal UserDetailsImpl userDetails){
        log.info("# get Chat Room, roomID : " + roomCode);
        ChatRoom room = chatService.findRoom(roomCode);
        model.addAttribute("room", room);
        model.addAttribute("userDetails",userDetails);
        List<Member> allMember = memberService.findAllMember();
        List<Member> inMember=new ArrayList<>();
        List<ChatMessage> chatMessages = chatService.loadMessage(roomCode);
        model.addAttribute("chatMessages",chatMessages);
        for (Member member : allMember){
            if(chatService.userInChatRoom(room,member)){
                inMember.add(member);
            }
        }
        if(room.getUserLimit()==0 || room.getCountUser()>= room.getUserLimit()){
            return "redirect:/chat/rooms";
        }else {
            if(chatService.ifExistSaveRoomJoin(userDetails.returnProfile().getId(),room.getId())){
                chatService.saveChatRoomJoinGuest(userDetails.returnProfile(),room);
            }
            ChatRoomJoin chatRoomJoin = chatService.modelUpChatRoomJoin(room,userDetails.returnProfile());
            if (chatRoomJoin.getRoomOwner().toString()=="OWNER"){
                model.addAttribute("inMember",inMember);
            }
            if(chatRoomJoin.getDropUserRoom()==1){
                return "redirect:/chat/rooms";
            }
            return "/chat/room";
        }
    }

    @GetMapping("/chat/new")
    public String make(Model model){
        ChatRoom chatRoom = new ChatRoom();
        model.addAttribute("form",chatRoom);
        return "/chat/newRoom";
    }

    @PostMapping("/room/new")
    public String makeRoom(ChatRoom chatRoom,@AuthenticationPrincipal UserDetailsImpl userDetails){
        chatService.createChatRoom(chatRoom);
        chatService.saveChatRoomJoinOwner(userDetails.returnProfile(),chatRoom);
        return "redirect:/chat/rooms";
    }

}