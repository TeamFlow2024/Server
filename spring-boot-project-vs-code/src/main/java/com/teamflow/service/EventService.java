package com.teamflow.service;

import com.teamflow.dto.EventResponseDto;
import com.teamflow.dto.PersonalEventDto;
import com.teamflow.dto.TeamEventDto;
import com.teamflow.model.*;
import com.teamflow.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.ArrayList;


import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EventService {

    private final EventRepository eventRepository;
    private final PersonalScheduleRepository personalScheduleRepository;
    private final TeamScheduleRepository teamScheduleRepository;
    private final TeamRepository teamRepository;

    public EventResponseDto addEventToPersonalSchedule(PersonalEventDto dto, User user) {
        PersonalSchedule schedule = personalScheduleRepository
    .findByUser_Id(user.getId()) // <- Long 타입 id 사용
    .orElseThrow(() -> new RuntimeException("개인 캘린더를 찾을 수 없습니다."));

    
        Event event = new Event();
        event.setPersonalSchedule(schedule);
        event.setTitle(dto.getTitle());
        event.setStartTime(dto.getStartTime());
        event.setEndTime(dto.getEndTime());
        event.setColor(dto.getColor());
    
        return convertToDto(eventRepository.save(event));
    }
    
    public EventResponseDto addEventToTeamSchedule(TeamEventDto dto) {
        Team team = teamRepository.findById(dto.getTeamId())
                .orElseThrow(() -> new RuntimeException("Team not found"));
    
        TeamSchedule schedule = teamScheduleRepository.findByTeam(team)
                .orElseThrow(() -> new RuntimeException("Team schedule not found"));
    
        Event event = new Event();
        event.setTeamSchedule(schedule);
        event.setTitle(dto.getTitle());
        event.setStartTime(dto.getStartTime());
        event.setEndTime(dto.getEndTime());
        event.setColor(dto.getColor());
    
        return convertToDto(eventRepository.save(event));
    }
    

    // ✅ 내 모든 이벤트 조회 (개인 + 소속 팀)
    public List<EventResponseDto> getEventsForUser(User user) {
        // ✅ 개인 일정 가져오기
        List<Event> events = personalScheduleRepository.findByUser_Id(user.getId())
        .map(eventRepository::findAllByPersonalSchedule)
        .map(ArrayList::new)
        .orElse(new ArrayList<>());

    
        // ✅ 내가 속한 팀들의 일정 가져오기
        List<Team> teams = user.getTeamMembers().stream()
                .map(TeamMembers::getTeam)
                .distinct()
                .toList();
    
        for (Team team : teams) {
            teamScheduleRepository.findByTeam(team).ifPresent(schedule -> {
                events.addAll(eventRepository.findAllByTeamSchedule(schedule)); // ✅ 이제 잘 들어감
            });
        }
    
        // ✅ 모두 DTO로 변환해서 반환
        return events.stream().map(this::convertToDto).toList();
    }
    

    public List<EventResponseDto> getPersonalEvents(User user) {
        return personalScheduleRepository.findByUser_Id(user.getId())
        .map(eventRepository::findAllByPersonalSchedule)
        .orElse(List.of())
        .stream()
        .map(this::convertToDto)
        .toList();

    }


    public List<EventResponseDto> getAllEventsBySchedule(Long scheduleId) {
        List<Event> events = eventRepository.findAllByTeamSchedule(new TeamSchedule() {{
            setTeamId(scheduleId);
        }});

        return events.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    public List<EventResponseDto> getEventsByScheduleAndDate(Long scheduleId, LocalDate date) {
        List<Event> events = eventRepository.findAllByTeamScheduleAndDate(new TeamSchedule() {{
            setTeamId(scheduleId);
        }}, date);

        return events.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    public List<EventResponseDto> getEventsByTeam(Long teamId) {
        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new RuntimeException("Team not found"));

        TeamSchedule schedule = teamScheduleRepository.findByTeam(team)
                .orElseThrow(() -> new RuntimeException("Team schedule not found"));

        return eventRepository.findAllByTeamSchedule(schedule).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    private EventResponseDto convertToDto(Event event) {
        EventResponseDto dto = new EventResponseDto();
        dto.setEventId(event.getEventId());
        dto.setTitle(event.getTitle());
        dto.setStartTime(event.getStartTime());
        dto.setEndTime(event.getEndTime());
        dto.setColor(event.getColor());

        if (event.getTeamSchedule() != null) {
            dto.setType("TEAM");
            dto.setTeamId(event.getTeamSchedule().getTeamId());
        } else if (event.getPersonalSchedule() != null) {
            dto.setType("PERSONAL");
            dto.setPersonalScheduleId(event.getPersonalSchedule().getId());
        }

        return dto;
    }
}