package com.dain.repository.alarm;

import com.dain.domain.entity.Alarm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlarmRepository extends JpaRepository<Alarm, Long>, AlarmCustomRepository {

    int countByMemberId(Long userId);
}
