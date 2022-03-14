package com.dain.service;

import com.dain.domain.dto.ReplyDTO;
import com.dain.domain.entity.Alarm;
import com.dain.domain.entity.Board;
import com.dain.domain.entity.Member;
import com.dain.repository.MemberRepository;
import com.dain.repository.board.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AlarmService {

    private final SimpMessagingTemplate messageTemplate;
    private final MemberRepository memberRepository;
    private final BoardRepository boardRepository;

    public void message(ReplyDTO replyDTO, Long boardId, Long userId){

        Member fromMember = memberRepository.findById(userId).get();
        Board board = boardRepository.findById(boardId).get();
        Member toMember = memberRepository.findById(board.getMember().getId()).get();

        messageTemplate.convertAndSend("/sub/" + fromMember.getId(), new Alarm(fromMember, toMember, replyDTO.getContent()));
    }
}
