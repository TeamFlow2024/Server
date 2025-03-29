package com.teamflow.service;

import com.teamflow.dto.EventDto;
import com.teamflow.dto.EventResponseDto;
import com.teamflow.model.*;
import com.teamflow.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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

    // ✅ 개인 일정 추가
    public EventResponseDto addEventToPersonalSchedule(EventDto dto, User user) {
        PersonalSchedule schedule = personalScheduleRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("개인 캘린더를 찾을 수 없습니다."));

        Event event = new Event();
        event.setPersonalSchedule(schedule);
        event.setTitle(dto.getTitle());
        event.setStartTime(dto.getStartTime());
        event.setEndTime(dto.getEndTime());
        event.setColor(dto.getColor());

        return convertToDto(eventRepository.save(event));
    }

    // ✅ 팀 일정 추가
    public EventResponseDto addEventToTeamSchedule(EventDto dto) {
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
        List<Event> events = personalScheduleRepository.findByUser(user)
                .map(eventRepository::findAllByPersonalSchedule)
                .orElse(List.of());

        List<Team> teams = user.getTeamMembers().stream()
                .map(TeamMembers::getTeam)
                .distinct()
                .collect(Collectors.toList());

        for (Team team : teams) {
            teamScheduleRepository.findByTeam(team).ifPresent(schedule -> {
                events.addAll(eventRepository.findAllByTeamSchedule(schedule));
            });
        }

        return events.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    public EventResponseDto updateEvent(Long eventId, EventDto dto) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Event not found"));

        if (dto.getTitle() != null) event.setTitle(dto.getTitle());
        if (dto.getStartTime() != null) event.setStartTime(dto.getStartTime());
        if (dto.getEndTime() != null) event.setEndTime(dto.getEndTime());
        if (dto.getColor() != null) event.setColor(dto.getColor());

        return convertToDto(eventRepository.save(event));
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