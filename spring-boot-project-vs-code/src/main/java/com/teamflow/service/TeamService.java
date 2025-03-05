package com.teamflow.service;

import com.teamflow.model.Team;
import com.teamflow.model.TeamMembers;
import com.teamflow.model.User;
import com.teamflow.repository.TeamRepository;
import com.teamflow.repository.TeamMembersRepository;
import com.teamflow.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TeamService {
    private final TeamRepository teamRepository;
    private final TeamMembersRepository teamMembersRepository;
    private final UserRepository userRepository;

    // 1️⃣ 팀 생성
    public Team createTeam(String teamName, String teamColor, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Team team = new Team();
        team.setTeamName(teamName);
        team.setTeamColor(teamColor);
        team.setUser(user);

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
