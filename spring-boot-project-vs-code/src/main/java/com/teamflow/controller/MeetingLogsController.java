package com.teamflow.controller;

import com.teamflow.dto.MeetingLogsDto;
import com.teamflow.model.MeetingLogs;
import com.teamflow.service.MeetingLogsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/meeting-logs")
@RequiredArgsConstructor
public class MeetingLogsController {
    private final MeetingLogsService meetingLogsService;

    // 회의록 생성 (날짜 및 시작 시간 포함)
    @PostMapping
    public ResponseEntity<MeetingLogs> createMeetingLog(@RequestBody MeetingLogsDto dto) {
        MeetingLogs log = meetingLogsService.createMeetingLog(dto);
        return ResponseEntity.ok(log);
    }

    // 특정 팀의 모든 회의록 조회
    @GetMapping("/{teamId}")
    public ResponseEntity<List<MeetingLogs>> getMeetingLogsByTeam(@PathVariable Long teamId) {
        List<MeetingLogs> logs = meetingLogsService.getMeetingLogsByTeam(teamId);
        return ResponseEntity.ok(logs);
    }

    // ✅ 특정 회의록 1개 조회 (logId 기준)
    @GetMapping("/log/{logId}")
    public ResponseEntity<MeetingLogs> getMeetingLogById(@PathVariable Long logId) {
        MeetingLogs log = meetingLogsService.getMeetingLogById(logId);
        return ResponseEntity.ok(log);
    }

    // 회의록 삭제
    @DeleteMapping("/{logId}")
    public ResponseEntity<Void> deleteMeetingLog(@PathVariable Long logId) {
        meetingLogsService.deleteMeetingLog(logId);
        return ResponseEntity.noContent().build();
    }

    // 파일 업로드
    @PostMapping("/upload-audio")
    public ResponseEntity<String> uploadAudioFile(@RequestParam("file") MultipartFile file) throws IOException {
        String filePath = meetingLogsService.uploadAudioFile(file);
        return ResponseEntity.ok(filePath);
    }
}
