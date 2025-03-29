package com.teamflow.service;

import com.teamflow.model.Team;
import com.teamflow.model.TeamMembers;
import com.teamflow.model.User;
import com.teamflow.repository.TeamRepository;
import com.teamflow.repository.TeamMembersRepository;
import com.teamflow.repository.UserRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.stereotype.Service;

import com.teamflow.dto.TeamSummaryDto;
import java.util.ArrayList;


@Service
@RequiredArgsConstructor
public class TeamService {
    private final TeamRepository teamRepository;
    private final TeamMembersRepository teamMembersRepository;
    private final UserRepository userRepository;
    private final TeamScheduleService teamScheduleService; // ✅ 추가 필요!


    public Team createTeam(String teamName, String teamColor, String ownerUserId, List<String> memberIds){
        // 🔍 owner 조회 (userId 기반)
        User owner = userRepository.findByUserId(ownerUserId)
                .orElseThrow(() -> new RuntimeException("User not found: " + ownerUserId));
    
        // 🧩 팀 생성
        Team team = new Team();
        team.setTeamName(teamName);
        team.setTeamColor(teamColor);
        team.setUser(owner); // 팀 생성자(owner)
        team = teamRepository.save(team);

        teamScheduleService.createTeamSchedule(team);
    
        // 🔥 [1] 오너도 팀 멤버로 등록
        TeamMembers ownerMember = new TeamMembers();
        ownerMember.setTeam(team);
        ownerMember.setUser(owner);
        ownerMember.setRole("OWNER");
        ownerMember.setProfile(owner.getProfile() != null ? owner.getProfile() : "default_profile.png"); // ✅ 프로필 경로 넣기
        teamMembersRepository.save(ownerMember);

    
        // 🔥 [2] 나머지 멤버들 추가
        for (String memberUserId : memberIds) {
            User member = userRepository.findByUserId(memberUserId)
                    .orElseThrow(() -> new RuntimeException("User not found: " + memberUserId));
    
            TeamMembers teamMember = new TeamMembers();
            teamMember.setTeam(team);
            teamMember.setUser(member);
            teamMember.setRole("MEMBER"); // 일반 멤버
            teamMember.setProfile(member.getProfile() != null ? member.getProfile() : "default_profile.png");
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
    public List<TeamMembers> addTeamMembers(Long teamId, List<String> userIds, String requesterUserId) {
        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new RuntimeException("Team not found"));
    
        // 🔐 오너 검증
        if (!team.getUser().getUserId().equals(requesterUserId)) {
            throw new RuntimeException("권한이 없습니다. 이 팀의 오너가 아닙니다.");
        }
    
        List<TeamMembers> savedMembers = new ArrayList<>();
        for (String userId : userIds) {
            User user = userRepository.findByUserId(userId)
                    .orElseThrow(() -> new RuntimeException("User not found: " + userId));
            TeamMembers member = new TeamMembers();
            member.setTeam(team);
            member.setUser(user);
            member.setRole("MEMBER");
            member.setProfile(user.getProfile() != null ? user.getProfile() : "default_profile.png");
            savedMembers.add(teamMembersRepository.save(member));
        }
        return savedMembers;
    }
    

    // ✅ 내가 속한 팀 ID 목록 가져오기
    public List<TeamSummaryDto> getTeamSummariesByUserId(String userId) {
        User user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("User not found: " + userId));
    
        List<TeamMembers> teamMemberships = user.getTeamMembers();
    
        List<TeamSummaryDto> summaries = new java.util.ArrayList<>();
        for (TeamMembers membership : teamMemberships) {
            summaries.add(TeamSummaryDto.fromEntity(membership.getTeam()));
        }
    
        return summaries;
    }


}
