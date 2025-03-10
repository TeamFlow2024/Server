package com.teamflow.service;

import com.teamflow.dto.MeetingLogsDto;
import com.teamflow.model.MeetingLogs;
import com.teamflow.model.Team;
import com.teamflow.repository.MeetingLogsRepository;
import com.teamflow.repository.TeamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MeetingLogsService {
    private final MeetingLogsRepository meetingLogsRepository;
    private final TeamRepository teamRepository;

    private final String UPLOAD_DIR = "uploads/audio/"; // 파일 저장 디렉토리

    // 회의록 생성
    @PreAuthorize("hasRole('USER')") // 인증된 사용자만 접근 가능
    public MeetingLogs createMeetingLog(MeetingLogsDto dto) {
        Team team = teamRepository.findById(dto.getTeamId())
                .orElseThrow(() -> new IllegalArgumentException("팀이 존재하지 않습니다."));

        MeetingLogs log = new MeetingLogs();
        log.setTeam(team);
        log.setLogText(dto.getLogText());
        log.setMeetingDate(dto.getMeetingDate() != null ? dto.getMeetingDate() : LocalDate.now()); // 회의 날짜 설정
        log.setTimestamp(dto.getTimestamp() != null ? dto.getTimestamp() : LocalDateTime.now()); // 회의 시작 시간 설정

        return meetingLogsRepository.save(log);
    }

    // 특정 팀의 모든 회의록 조회
    @PreAuthorize("isAuthenticated()")
    public List<MeetingLogs> getMeetingLogsByTeam(Long teamId) {
        return meetingLogsRepository.findByTeam_TeamId(teamId);
    }

    // ✅ 특정 회의록 1개 조회 (logId 기준)
    @PreAuthorize("isAuthenticated()")
    public MeetingLogs getMeetingLogById(Long logId) {
        return meetingLogsRepository.findById(logId)
                .orElseThrow(() -> new IllegalArgumentException("해당 회의록이 존재하지 않습니다."));
    }

    // 회의록 삭제
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public void deleteMeetingLog(Long logId) {
        meetingLogsRepository.deleteById(logId);
    }

    // 파일 업로드 (음성 파일 저장)
    @PreAuthorize("isAuthenticated()")
    public String uploadAudioFile(MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            throw new IllegalArgumentException("파일이 비어 있습니다.");
        }

        // 저장 경로 설정
        String filePath = UPLOAD_DIR + file.getOriginalFilename();
        File dest = new File(filePath);
        file.transferTo(dest);

        return filePath;
    }
}
