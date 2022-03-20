package com.dain.controller.api;

import com.dain.domain.dto.ResponseAlarmDTO;
import com.dain.exception.ValidateDTO;
import com.dain.principal.UserDetailsImpl;
import com.dain.service.AlarmService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Log4j2
public class AlarmApiController {

    private final AlarmService alarmService;

    @GetMapping("/users/alarm")
    public ResponseEntity<?> getAlarm(@AuthenticationPrincipal UserDetailsImpl userDetails){

        List<ResponseAlarmDTO> alarmList = alarmService.getAlarmList(userDetails.returnProfile().getId());

        return new ResponseEntity<>(new ValidateDTO<>(1, "success", alarmList), HttpStatus.OK);
    }

    @DeleteMapping("/alarm/{alarmId}")
    public ResponseEntity<?> deleteAlarm(@PathVariable("alarmId") String alarmId){

        alarmService.deleteAlarm(Long.parseLong(alarmId));

        return ResponseEntity.ok("success");
    }

    @PutMapping("/alarm/all-check")
    public ResponseEntity<?> alarmAllCheck(@AuthenticationPrincipal UserDetailsImpl userDetails){

        alarmService.allCheck(userDetails.returnProfile().getId());

        return ResponseEntity.ok("success");
    }

    @DeleteMapping("/alarm/all")
    public ResponseEntity<?> deleteAllAlarm(@AuthenticationPrincipal UserDetailsImpl userDetails){

        alarmService.deleteAllAlarm(userDetails.returnProfile().getId());

        return ResponseEntity.ok("success");
    }

    @PutMapping("/check/alarm/{alarmId}")
    public ResponseEntity<?> checkAlarm(@PathVariable("alarmId") String alarmId,
                                        @AuthenticationPrincipal UserDetailsImpl userDetails){

        alarmService.checkAlarm(alarmId, userDetails.returnProfile().getId());

        return ResponseEntity.ok("success");
    }
}
