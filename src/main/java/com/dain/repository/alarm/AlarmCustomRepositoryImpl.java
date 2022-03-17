package com.dain.repository.alarm;

import com.dain.domain.entity.Alarm;
import com.dain.domain.entity.QAlarm;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.extern.log4j.Log4j2;

import javax.persistence.EntityManager;

import java.util.List;

import static com.dain.domain.entity.QAlarm.alarm;

@Log4j2
public class AlarmCustomRepositoryImpl implements AlarmCustomRepository{

    private final JPAQueryFactory queryFactory;

    public AlarmCustomRepositoryImpl(EntityManager em){
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<Alarm> getAlarmList(Long userId) {

        List<Alarm> alarmList = queryFactory.selectFrom(alarm)
                .where(alarm.member.id.eq(userId))
                .where(alarm.checkAlarm.eq(0))
                .fetch();

        return alarmList;
    }
}
