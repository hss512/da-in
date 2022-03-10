package com.dain.service;

import com.dain.domain.dto.ReplyDTO;
import com.dain.domain.entity.Board;
import com.dain.domain.entity.Member;
import com.dain.domain.entity.Reply;
import com.dain.repository.MemberRepository;
import com.dain.repository.reply.ReplyRepository;
import com.dain.repository.board.BoardRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
@Log4j2
public class ReplyService {

    private final ReplyRepository replyRepository;
    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;


    public ReplyDTO replyWrite(String boardId, String userId, ReplyDTO replyDTO) {

        Optional<Board> board = boardRepository.findById(Long.parseLong(boardId));
        if(board.isEmpty()){
            throw new EntityNotFoundException("Board Empty");
        }
        Optional<Member> member = memberRepository.findById(Long.parseLong(userId));
        if(member.isEmpty()){
            throw new EntityNotFoundException("Member Empty");
        }

        Reply reply = Reply.builder()
                .content(replyDTO.getContent())
                .member(member.get())
                .board(board.get())
                .build();

        return replyRepository.save(reply).toReplyDTO();
    }

    public Page<ReplyDTO> getReply(String boardId, Pageable pageable) {
        return replyRepository.getReply(Long.parseLong(boardId), pageable);
    }
}
