package com.teamflow.service;

import com.teamflow.model.Team;
import com.teamflow.model.TeamSchedule;
import com.teamflow.repository.TeamRepository;
import com.teamflow.repository.TeamScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TeamScheduleService {

    private final TeamScheduleRepository teamScheduleRepository;
    private final TeamRepository teamRepository;

    public TeamSchedule createTeamSchedule(Team team) {
        TeamSchedule ts = new TeamSchedule();
        ts.setTeam(team);
        ts.setDescription(team.getTeamName() + " 팀 캘린더");
        return teamScheduleRepository.save(ts);
    }

    public TeamSchedule getByTeam(Team team) {
        return teamScheduleRepository.findByTeam(team)
                .orElseThrow(() -> new RuntimeException("팀 캘린더 없음"));
    }
}
