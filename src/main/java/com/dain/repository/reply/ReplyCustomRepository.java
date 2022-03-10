package com.dain.repository.reply;

import com.dain.domain.dto.ReplyDTO;
import com.dain.domain.entity.Reply;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ReplyCustomRepository {
    Page<ReplyDTO> getReply(Long boardId, Pageable pageable);
}
