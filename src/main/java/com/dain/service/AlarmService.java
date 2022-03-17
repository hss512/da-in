package com.dain.service;

import com.dain.domain.dto.MessageAlarmDTO;
import com.dain.domain.dto.ReplyDTO;
import com.dain.domain.dto.ResponseAlarmDTO;
import com.dain.domain.entity.Alarm;
import com.dain.domain.entity.Board;
import com.dain.domain.entity.Member;
import com.dain.repository.alarm.AlarmRepository;
import com.dain.repository.MemberRepository;
import com.dain.repository.board.BoardRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Log4j2
public class AlarmService {

    private final AlarmRepository alarmRepository;
    private final BoardRepository boardRepository;

    public Alarm createAlarm(String boardId, MessageAlarmDTO alarmDTO) {
        Board board = boardRepository.findById(Long.parseLong(boardId)).get();
        Member toMember = board.getMember();

        Alarm alarm = new Alarm(toMember, alarmDTO.getContent(), Long.parseLong(boardId));

        return alarmRepository.save(alarm);
    }

    public int getAll(Long userId) {
        List<ResponseAlarmDTO> alarmList = getAlarmList(userId);
        return alarmList.size();
    }

    public List<ResponseAlarmDTO> getAlarmList(Long userId) {

        List<Alarm> alarmList = alarmRepository.getAlarmList(userId);

        return alarmList.stream().map(Alarm::toAlarmDTO).collect(Collectors.toList());
    }
}
