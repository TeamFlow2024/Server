package com.teamflow.repository;

import com.teamflow.model.TeamMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface TeamMessageRepository extends JpaRepository<TeamMessage, Long> {
    // 팀 내 최근 메시지 20개
    List<TeamMessage> findTop20ByTeamIdOrderByTimestampDesc(Long teamId);
}
