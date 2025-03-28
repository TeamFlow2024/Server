package com.teamflow.service;

import com.teamflow.dto.MeetingLogsDto;
import com.teamflow.model.MeetingLogs;
import com.teamflow.model.Team;
import com.teamflow.repository.MeetingLogsRepository;
import com.teamflow.repository.TeamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.security.access.prepost.PreAuthorize;
import com.teamflow.repository.TeamMembersRepository; // ✅ 수정
import com.teamflow.repository.UserRepository;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MeetingLogsService {
    private final MeetingLogsRepository meetingLogsRepository;
    private final TeamRepository teamRepository;
    private final TeamMembersRepository teamMembersRepository; // ✅ 추가
    private final UserRepository userRepository;

    // 회의록 생성
    public MeetingLogs createMeetingLog(MeetingLogsDto dto, String currentUserId) {
        // 1. 현재 유저가 해당 팀의 멤버인지 확인
        boolean isTeamMember = teamMembersRepository.existsByTeam_TeamIdAndUser_UserId(dto.getTeamId(), currentUserId); // ✅ 수정
        if (!isTeamMember) {
            throw new RuntimeException("이 팀에 속한 멤버가 아닙니다."); // ✅ 수정
        }

        // 2. 팀 엔티티 조회
        Team team = teamRepository.findById(dto.getTeamId())
                .orElseThrow(() -> new IllegalArgumentException("팀이 존재하지 않습니다."));

        // 3. 회의록 저장
        MeetingLogs log = new MeetingLogs();
        log.setTeam(team);
        log.setTitle(dto.getTitle());
        log.setLogText(dto.getLogText());
        log.setMeetingDate(dto.getMeetingDate() != null ? dto.getMeetingDate() : LocalDate.now());

        return meetingLogsRepository.save(log);
    }

    // 특정 팀의 모든 회의록 조회
    //@PreAuthorize("isAuthenticated()")
    public List<MeetingLogs> getMeetingLogsByTeam(Long teamId) {
        return meetingLogsRepository.findByTeam_TeamId(teamId);
    }

    // ✅ 특정 회의록 1개 조회 (logId 기준)
    //@PreAuthorize("isAuthenticated()")
    public MeetingLogs getMeetingLogById(Long logId) {
        return meetingLogsRepository.findById(logId)
                .orElseThrow(() -> new IllegalArgumentException("해당 회의록이 존재하지 않습니다."));
    }

    // 회의록 삭제
    //@PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public void deleteMeetingLog(Long logId) {
        meetingLogsRepository.deleteById(logId);
    }
}
