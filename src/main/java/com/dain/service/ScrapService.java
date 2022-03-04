package com.dain.service;

import com.dain.domain.entity.Scrap;
import com.dain.repository.MemberRepository;
import com.dain.repository.ScrapRepository;
import com.dain.repository.board.BoardRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import static java.lang.Long.parseLong;

@Service
@RequiredArgsConstructor
@Log4j2
public class ScrapService {

    private final ScrapRepository scrapRepository;
    private final MemberRepository memberRepository;
    private final BoardRepository boardRepository;

    public int doScrap(String userId, String boardId){

        if(scrapRepository.findScrapByMemberIdAndBoardId(parseLong(userId), parseLong(boardId)) == null){
            Scrap scrap = Scrap.builder()
                    .member(memberRepository.findById(parseLong(userId)).get())
                    .board(boardRepository.findById(parseLong(boardId)).get())
                    .build();

            scrapRepository.save(scrap);
            return 1;
        }else{
            Scrap scrap = scrapRepository.findScrapByMemberIdAndBoardId(parseLong(userId), parseLong(boardId));
            scrapRepository.delete(scrap);
            return 0;
        }
    }
}
