package com.teamflow.repository;

import com.teamflow.model.MeetingLogs;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface MeetingLogsRepository extends JpaRepository<MeetingLogs, Long> {
    // 특정 팀의 회의록 조회
    List<MeetingLogs> findByTeam_TeamId(Long teamId);
}
