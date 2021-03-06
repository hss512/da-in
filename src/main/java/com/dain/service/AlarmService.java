package com.dain.service;

import com.dain.domain.dto.RequestSocketDTO;
import com.dain.domain.dto.ResponseAlarmDTO;
import com.dain.domain.entity.Alarm;
import com.dain.domain.entity.Board;
import com.dain.domain.entity.Member;
import com.dain.repository.alarm.AlarmRepository;
import com.dain.repository.board.BoardRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
@Log4j2
public class AlarmService {

    private final AlarmRepository alarmRepository;
    private final BoardRepository boardRepository;

    public Alarm createAlarm(String boardId, RequestSocketDTO alarmDTO) {
        Board board = boardRepository.findById(Long.parseLong(boardId)).get();
        Member toMember = board.getMember();

        Alarm alarm = new Alarm(toMember, alarmDTO.getContent(), Long.parseLong(boardId));

        return alarmRepository.save(alarm);
    }

    @Transactional(readOnly = true)
    public int getAll(Long userId) {
        List<ResponseAlarmDTO> alarmList = getAlarmList(userId);

        return (int)alarmList.stream().filter(alarm-> alarm.getCheck() == 0).count();
    }

    @Transactional(readOnly = true)
    public List<ResponseAlarmDTO> getAlarmList(Long userId) {

        List<Alarm> alarmList = alarmRepository.getAlarmList(userId);

        return alarmList.stream().map(Alarm::toAlarmDTO).collect(Collectors.toList());
    }

    public void deleteAlarm(Long alarmId) {
        alarmRepository.deleteById(alarmId);
    }

    public void allCheck(Long userId) {
        List<Alarm> alarms = alarmRepository.allCheck(userId);
        for (Alarm alarm : alarms) {
            alarm.checkUpdate();
        }
    }

    public void deleteAllAlarm(Long userId) {
        alarmRepository.deleteByMemberId(userId);
    }

    public void checkAlarm(String alarmId, Long memberId) {
        Alarm alarm = alarmRepository.findById(Long.parseLong(alarmId)).get();
        if(alarm.getMember().getId().equals(memberId)){
            alarm.checkUpdate();
        }
    }
}
