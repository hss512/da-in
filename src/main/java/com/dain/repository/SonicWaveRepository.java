package com.dain.repository;

import com.dain.domain.entity.SonicWave;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SonicWaveRepository extends JpaRepository<SonicWave,Long> {

    @Query("select id from SonicWave")
    List<SonicWave> findAllList ();
}
