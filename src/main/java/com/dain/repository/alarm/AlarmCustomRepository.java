package com.dain.repository.alarm;

import com.dain.domain.entity.Alarm;

import java.util.List;

public interface AlarmCustomRepository {
    List<Alarm> getAlarmList(Long userId);

    List<Alarm> allCheck(Long userId);
}
