package com.teamflow.service;

import com.teamflow.dto.ScheduleDto;
import com.teamflow.model.Schedule;
import com.teamflow.model.ScheduleType;
import com.teamflow.model.Team;
import com.teamflow.model.User;
import com.teamflow.repository.ScheduleRepository;
import com.teamflow.repository.TeamRepository;
import com.teamflow.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final UserRepository userRepository;
    private final TeamRepository teamRepository;

    public Schedule createSchedule(ScheduleDto dto) {
        Schedule schedule = new Schedule();
        schedule.setType(ScheduleType.valueOf(dto.getType().toUpperCase())); // ✅ Enum 타입에 맞게 수정

        if ("PERSONAL".equals(dto.getType())) {
            User user = userRepository.findById(dto.getUserId())
                    .orElseThrow(() -> new RuntimeException("User not found"));
            schedule.setUser(user);
            schedule.setDescription(dto.getDescription());
        } else if ("TEAM".equals(dto.getType())) {
            Team team = teamRepository.findById(dto.getTeamId())
                    .orElseThrow(() -> new RuntimeException("Team not found"));
            schedule.setTeam(team);
            schedule.setDescription(dto.getDescription());
        }

        return scheduleRepository.save(schedule);
    }

    public List<Schedule> getSchedulesForUser(User user) {
        List<Schedule> schedules = new ArrayList<>();

        // 개인 캘린더 추가
        scheduleRepository.findByUser(user).ifPresent(schedules::add);

        // 사용자가 속한 팀 캘린더 추가 (팀 이름으로 조회)
        List<Team> teams = teamRepository.findAllByTeamNameIn(user.getMyTeam());
        schedules.addAll(scheduleRepository.findAllByTeamIn(teams));

        return schedules;
    }

}
