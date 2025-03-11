package com.teamflow.service;

import com.teamflow.model.Schedule;
import com.teamflow.model.ScheduleType;
import com.teamflow.model.Team;
import com.teamflow.model.TeamMembers;
import com.teamflow.model.User;
import com.teamflow.repository.TeamRepository;
import com.teamflow.repository.TeamMembersRepository;
import com.teamflow.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.teamflow.model.Schedule;
import com.teamflow.model.Schedule;
import com.teamflow.repository.ScheduleRepository;

@Service
@RequiredArgsConstructor
public class TeamService {
    private final TeamRepository teamRepository;
    private final TeamMembersRepository teamMembersRepository;
    private final UserRepository userRepository;
    private final ScheduleRepository scheduleRepository;

    public Team createTeam(String teamName, String teamColor, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // 팀 캘린더 생성
        Schedule schedule = new Schedule();
        schedule.setType(ScheduleType.TEAM);
        schedule.setDescription(teamName + " 캘린더");
        schedule = scheduleRepository.save(schedule);

        // 팀 생성 및 캘린더 연결 (🔑 추가된 부분!)
        Team team = new Team();
        team.setTeamName(teamName);
        team.setTeamColor(teamColor);
        team.setUser(user);
        team.setSchedule(schedule); // <-- 이 부분이 필수입니다.

        return teamRepository.save(team);
    }

    // 2️⃣ 특정 팀 정보 조회
    public Team getTeamById(Long teamId) {
        return teamRepository.findById(teamId)
                .orElseThrow(() -> new RuntimeException("Team not found"));
    }

    // 3️⃣ 팀 멤버 추가
    public TeamMembers addTeamMember(Long teamId, Long userId, String role, String memberColor) {
        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new RuntimeException("Team not found"));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        TeamMembers teamMember = new TeamMembers();
        teamMember.setTeam(team);
        teamMember.setUser(user);
        teamMember.setRole(role);
        teamMember.setMemberColor(memberColor);

        return teamMembersRepository.save(teamMember);
    }
}
