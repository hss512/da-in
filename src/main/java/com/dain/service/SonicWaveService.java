package com.dain.service;

import com.dain.domain.entity.SonicWave;
import com.dain.repository.SonicWaveRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SonicWaveService {

    private final SonicWaveRepository sonicWaveRepository;

    public List<SonicWave> findAllSensor(){
        List<SonicWave> all = sonicWaveRepository.findAll();
        Collections.reverse(all);
        return all;
    }
}
