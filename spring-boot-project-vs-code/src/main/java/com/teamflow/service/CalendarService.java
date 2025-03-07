package com.teamflow.service;

import com.teamflow.dto.CalendarDto;
import com.teamflow.model.Calendar;
import com.teamflow.model.CalendarType;
import com.teamflow.model.Team;
import com.teamflow.model.User;
import com.teamflow.repository.CalendarRepository;
import com.teamflow.repository.TeamRepository;
import com.teamflow.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CalendarService {

    private final CalendarRepository calendarRepository;
    private final UserRepository userRepository;
    private final TeamRepository teamRepository;

    public Calendar createCalendar(CalendarDto dto) {
        Calendar calendar = new Calendar();
        calendar.setType(CalendarType.valueOf(dto.getType()));

        if ("PERSONAL".equals(dto.getType())) {
            User user = userRepository.findById(dto.getUserId())
                    .orElseThrow(() -> new RuntimeException("User not found"));
            calendar.setUser(user);
            calendar.setDescription(dto.getDescription());
        } else if ("TEAM".equals(dto.getType())) {
            Team team = teamRepository.findById(dto.getTeamId())
                    .orElseThrow(() -> new RuntimeException("Team not found"));
            calendar.setTeam(team);
            calendar.setDescription(dto.getDescription());
        }

        return calendarRepository.save(calendar);
    }

    public List<Calendar> getCalendarsForUser(User user) {
        List<Calendar> calendars = new ArrayList<>();

        // 개인 캘린더 추가
        calendarRepository.findByUser(user).ifPresent(calendars::add);

        // 사용자가 속한 팀 캘린더 추가 (팀 이름으로 조회)
        List<Team> teams = teamRepository.findAllByTeamNameIn(user.getMyTeam());
        calendars.addAll(calendarRepository.findAllByTeamIn(teams));

        return calendars;
    }

}
