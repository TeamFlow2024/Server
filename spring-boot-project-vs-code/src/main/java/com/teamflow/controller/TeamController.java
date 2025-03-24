package com.teamflow.controller;

import com.teamflow.model.Team;
import com.teamflow.model.TeamMembers;
import com.teamflow.service.TeamService;
import com.teamflow.dto.TeamRequest;
import com.teamflow.dto.TeamMemberRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/teams")
@RequiredArgsConstructor
public class TeamController {
    private final TeamService teamService;

    // 🟢 팀 생성 (POST /api/teams)
    @PostMapping
    public ResponseEntity<?> createTeam(@RequestBody TeamRequest request) {
        Team team = teamService.createTeam(
                request.getTeamName(),
                request.getTeamColor(),
                request.getOwnerId(),
                request.getMemberIds(),
                request.getRoles(),
                request.getMemberColors());

        return ResponseEntity.ok(Map.of("teamId", team.getTeamId(), "message", "팀이 생성되었습니다."));
    }

    // 🟡 팀 정보 조회 (GET /api/teams/{teamId})
    @GetMapping("/{teamId}")
    public ResponseEntity<?> getTeamById(@PathVariable Long teamId) {
        Team team = teamService.getTeamById(teamId);
        return ResponseEntity.ok(team);
    }

    // 🔵 여러 팀 멤버 추가 (PATCH /api/teams/{teamId}/members)
    @PatchMapping("/{teamId}/members")
    public ResponseEntity<?> addTeamMembers(@PathVariable Long teamId, @RequestBody TeamMemberRequest request) {
        teamService.addTeamMembers(
                teamId,
                request.getUserIds(),
                request.getRoles(),
                request.getMemberColors());

        return ResponseEntity.ok(Map.of("message", "팀 멤버들이 추가되었습니다."));
    }
}
