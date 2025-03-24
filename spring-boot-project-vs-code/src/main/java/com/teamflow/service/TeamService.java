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

import java.util.List;

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

    public Team createTeam(String teamName, String teamColor, Long ownerId, List<Long> memberIds, List<String> roles,
            List<String> memberColors) {
        User owner = userRepository.findById(ownerId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // 팀 캘린더 생성
        Schedule schedule = new Schedule();
        schedule.setType(ScheduleType.TEAM);
        schedule.setDescription(teamName + " 캘린더");
        schedule = scheduleRepository.save(schedule);

        // 팀 생성
        Team team = new Team();
        team.setTeamName(teamName);
        team.setTeamColor(teamColor);
        team.setUser(owner); // 팀장
        team.setSchedule(schedule);
        team = teamRepository.save(team); // 저장 후 teamId 사용

        // 팀 멤버 추가 (owner 포함 X)
        for (int i = 0; i < memberIds.size(); i++) {
            Long userId = memberIds.get(i);
            String role = roles.get(i);
            String color = memberColors.get(i);

            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new RuntimeException("User not found"));

            TeamMembers teamMember = new TeamMembers();
            teamMember.setTeam(team);
            teamMember.setUser(user);
            teamMember.setRole(role);
            teamMember.setMemberColor(color);
            teamMembersRepository.save(teamMember);
        }

        return team;
    }

    // 2️⃣ 특정 팀 정보 조회
    public Team getTeamById(Long teamId) {
        return teamRepository.findByIdWithMembers(teamId)
                .orElseThrow(() -> new RuntimeException("Team not found"));
    }

    // 3️⃣ 팀 멤버 추가
    public List<TeamMembers> addTeamMembers(Long teamId, List<Long> userIds, List<String> roles,
            List<String> memberColors) {
        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new RuntimeException("Team not found"));

        List<TeamMembers> savedMembers = new java.util.ArrayList<>();

        for (int i = 0; i < userIds.size(); i++) {
            User user = userRepository.findById(userIds.get(i))
                    .orElseThrow(() -> new RuntimeException("User not found"));

            TeamMembers teamMember = new TeamMembers();
            teamMember.setTeam(team);
            teamMember.setUser(user);
            teamMember.setRole(roles.get(i));
            teamMember.setMemberColor(memberColors.get(i));
            savedMembers.add(teamMembersRepository.save(teamMember));
        }

        return savedMembers;
    }
}
