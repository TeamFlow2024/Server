package com.teamflow.controller;

import com.teamflow.model.Team;
import com.teamflow.model.TeamMembers;
import com.teamflow.service.TeamService;
import com.teamflow.service.UserService;
import com.teamflow.dto.TeamRequest;
import com.teamflow.dto.TeamMemberRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;
import com.teamflow.dto.TeamResponseDto;
import com.teamflow.dto.TeamSummaryDto;



@RestController
@RequestMapping("/api/teams")
@RequiredArgsConstructor
public class TeamController {
    private final TeamService teamService;
    private final UserService userService;

    // 🟢 팀 생성 (POST /api/teams)
    @PostMapping
    public ResponseEntity<?> createTeam(@RequestBody TeamRequest request) {
        Team team = teamService.createTeam(
                request.getTeamName(),
                request.getTeamColor(),
                request.getOwnerId(),  // 이게 이제 String (userId) 여야 함
                request.getMemberIds());

        return ResponseEntity.ok(Map.of("teamId", team.getTeamId(), "message", "팀이 생성되었습니다."));
    }


    // 🟡 팀 조회
    @GetMapping("/{teamId}")
    public ResponseEntity<?> getTeamById(@PathVariable Long teamId) {
        Team team = teamService.getTeamById(teamId);
        TeamResponseDto dto = new TeamResponseDto(team);
        return ResponseEntity.ok(dto); // ✅ 안전한 DTO로 반환
    }


    // 🔵 팀 멤버 추가
    @PatchMapping("/{teamId}/members")
    public ResponseEntity<?> addTeamMembers(@PathVariable Long teamId, @RequestBody TeamMemberRequest request) {
        teamService.addTeamMembers(teamId, request.getUserIds());
        return ResponseEntity.ok(Map.of("message", "팀 멤버들이 추가되었습니다."));
    }

    // 🔴 내가 속한 팀 ID 목록 조회 (GET /api/teams/my)
    @GetMapping("/my")
    public ResponseEntity<?> getMyTeams(@RequestHeader("userId") String userId) {
        List<TeamSummaryDto> myTeams = teamService.getTeamSummariesByUserId(userId);
        return ResponseEntity.ok(Map.of("myTeams", myTeams));
    }
}
