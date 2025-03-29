package com.teamflow.controller;

import com.teamflow.dto.MeetingLogsDto;
import com.teamflow.model.MeetingLogs;
import com.teamflow.service.MeetingLogsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.annotation.AuthenticationPrincipal; 
import org.springframework.security.core.userdetails.UserDetails;
import com.teamflow.dto.MeetingLogResponseDto;

import java.util.List;

@RestController
@RequestMapping("/api/meeting-logs")
@RequiredArgsConstructor
public class MeetingLogsController {
    private final MeetingLogsService meetingLogsService;

    // 회의록 생성 (날짜 및 시작 시간 포함)
    @PostMapping
    public ResponseEntity<MeetingLogResponseDto> createMeetingLog(@RequestBody MeetingLogsDto dto,
                                                                @AuthenticationPrincipal UserDetails userDetails) {
        String currentUserId = userDetails.getUsername();
        MeetingLogs log = meetingLogsService.createMeetingLog(dto, currentUserId);

        // 👉 DTO 변환
        MeetingLogResponseDto response = new MeetingLogResponseDto(
            log.getLogId(),
            log.getTeam().getTeamId(),
            log.getTitle(),
            log.getLogText(),
            log.getMeetingDate()
        );

        return ResponseEntity.ok(response);
    }


    // 특정 팀의 모든 회의록 조회
    @GetMapping("/{teamId}")
    public ResponseEntity<List<MeetingLogResponseDto>> getMeetingLogsByTeam(@PathVariable Long teamId) {
        List<MeetingLogs> logs = meetingLogsService.getMeetingLogsByTeam(teamId);

        List<MeetingLogResponseDto> response = logs.stream()
                .map(log -> new MeetingLogResponseDto(
                        log.getLogId(),
                        log.getTeam().getTeamId(),
                        log.getTitle(),
                        log.getLogText(),
                        log.getMeetingDate()
                ))
                .toList();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/log/{logId}")
    public ResponseEntity<MeetingLogResponseDto> getMeetingLogById(@PathVariable Long logId) {
        MeetingLogs log = meetingLogsService.getMeetingLogById(logId);

        MeetingLogResponseDto response = new MeetingLogResponseDto(
                log.getLogId(),
                log.getTeam().getTeamId(),
                log.getTitle(),
                log.getLogText(),
                log.getMeetingDate()
        );

        return ResponseEntity.ok(response);
    }


    // 회의록 삭제
    @DeleteMapping("/{logId}")
    public ResponseEntity<Void> deleteMeetingLog(@PathVariable Long logId) {
        meetingLogsService.deleteMeetingLog(logId);
        return ResponseEntity.noContent().build();
    }
}
