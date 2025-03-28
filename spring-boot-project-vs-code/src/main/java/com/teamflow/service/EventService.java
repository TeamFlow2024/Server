package com.teamflow.service;

import com.teamflow.dto.EventDto;
import com.teamflow.dto.EventResponseDto;
import com.teamflow.model.Schedule;
import com.teamflow.model.Event;
import com.teamflow.model.Team;
import com.teamflow.model.User;
import com.teamflow.repository.ScheduleRepository;
import com.teamflow.repository.EventRepository;
import com.teamflow.repository.TeamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EventService {

    private final EventRepository eventRepository;
    private final ScheduleRepository scheduleRepository;
    private final TeamRepository teamRepository;

    public EventResponseDto addEvent(Long scheduleId, EventDto dto) {
        Schedule schedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new RuntimeException("Schedule not found"));

        Event event = new Event();
        event.setSchedule(schedule);
        event.setTitle(dto.getTitle());
        event.setStartTime(dto.getStartTime());
        event.setEndTime(dto.getEndTime());
        event.setColor(dto.getColor());

        Event savedEvent = eventRepository.save(event);
        return convertToDto(savedEvent);
    }

    public List<EventResponseDto> getEventsForUser(User user) {
        // 사용자 개인 캘린더
        List<Schedule> schedules = scheduleRepository.findByUser(user)
                .map(List::of)
                .orElseGet(List::of);

        // 사용자 소속 팀의 팀 캘린더 추가
        List<Team> teams = user.getTeamMembers().stream()
    .map(tm -> tm.getTeam())
    .distinct()
    .collect(Collectors.toList());

        schedules.addAll(scheduleRepository.findAllByTeamIn(teams));
    
        // 해당 스케줄에 연결된 이벤트들 반환
        List<Event> events = eventRepository.findAllByScheduleIn(schedules);
    
        return events.stream()
            .map(this::convertToDto)
            .collect(Collectors.toList());
    }
    

    public EventResponseDto updateEvent(Long eventId, EventDto dto) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Event not found"));

        if (dto.getTitle() != null)
            event.setTitle(dto.getTitle());
        if (dto.getStartTime() != null)
            event.setStartTime(dto.getStartTime());
        if (dto.getEndTime() != null)
            event.setEndTime(dto.getEndTime());
        if (dto.getColor() != null)
            event.setColor(dto.getColor());

        Event updatedEvent = eventRepository.save(event);
        return convertToDto(updatedEvent);
    }

    public List<EventResponseDto> getAllEventsBySchedule(Long scheduleId) {
        Schedule schedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new RuntimeException("Schedule not found"));

        List<Event> events = eventRepository.findAllBySchedule(schedule);

        return events.stream()
                .map(event -> convertToDto(event))
                .collect(Collectors.toList());
    }

    // 🔥 추가된 코드 (캘린더 아이디 + 날짜로 이벤트 조회)
    public List<EventResponseDto> getEventsByScheduleAndDate(Long scheduleId, LocalDate date) {
        Schedule schedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new RuntimeException("Schedule not found"));

        List<Event> events = eventRepository.findAllByScheduleAndDate(schedule, date);

        return events.stream()
                .map(event -> convertToDto(event))
                .collect(Collectors.toList());
    }

    private EventResponseDto convertToDto(Event event) {
        EventResponseDto dto = new EventResponseDto();
        dto.setEventId(event.getEventId());
        dto.setTitle(event.getTitle());
        dto.setStartTime(event.getStartTime());
        dto.setEndTime(event.getEndTime());
        dto.setColor(event.getColor());
        dto.setScheduleId(event.getSchedule().getScheduleId());
        return dto;
    }
}
