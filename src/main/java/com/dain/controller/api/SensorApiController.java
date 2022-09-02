package com.dain.controller.api;

import com.dain.domain.entity.SonicWave;
import com.dain.repository.SonicWaveRepository;
import com.dain.service.SonicWaveService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Log4j2
@RequestMapping("/api/sensor")
public class SensorApiController {

    private final SonicWaveService service;

    @PostMapping("/allValueList")
    public List<SonicWave> sensorValueList(){
        List<SonicWave> allSensor = service.findAllSensor();
        return allSensor;
    }
}
