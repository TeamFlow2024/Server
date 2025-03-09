package com.teamflow.service;

import com.teamflow.dto.EventDto;
import com.teamflow.dto.EventResponseDto;
import com.teamflow.model.Calendar;
import com.teamflow.model.Event;
import com.teamflow.model.Team;
import com.teamflow.model.User;
import com.teamflow.repository.CalendarRepository;
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
    private final CalendarRepository calendarRepository;
    private final TeamRepository teamRepository;

    public EventResponseDto addEvent(Long calendarId, EventDto dto) {
        Calendar calendar = calendarRepository.findById(calendarId)
                .orElseThrow(() -> new RuntimeException("Calendar not found"));

        Event event = new Event();
        event.setCalendar(calendar);
        event.setTitle(dto.getTitle());
        event.setStartTime(dto.getStartTime());
        event.setEndTime(dto.getEndTime());
        event.setColor(dto.getColor());

        Event savedEvent = eventRepository.save(event);
        return convertToDto(savedEvent);
    }

    public List<EventResponseDto> getEventsForUser(User user) {
        // 사용자의 개인 캘린더 추가
        List<Calendar> calendars = calendarRepository.findByUser(user)
                .map(List::of)
                .orElseGet(List::of);

        // 사용자 소속 팀의 팀 캘린더 추가
        List<Team> teams = teamRepository.findAllByTeamNameIn(user.getMyTeam());
        calendars.addAll(calendarRepository.findAllByTeamIn(teams));

        List<Event> events = eventRepository.findAllByCalendarIn(calendars);

        return events.stream()
                .map(event -> convertToDto(event))
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

    public List<EventResponseDto> getAllEventsByCalendar(Long calendarId) {
        Calendar calendar = calendarRepository.findById(calendarId)
                .orElseThrow(() -> new RuntimeException("Calendar not found"));

        List<Event> events = eventRepository.findAllByCalendar(calendar);

        return events.stream()
                .map(event -> convertToDto(event))
                .collect(Collectors.toList());
    }

    // 🔥 추가된 코드 (캘린더 아이디 + 날짜로 이벤트 조회)
    public List<EventResponseDto> getEventsByCalendarAndDate(Long calendarId, LocalDate date) {
        Calendar calendar = calendarRepository.findById(calendarId)
                .orElseThrow(() -> new RuntimeException("Calendar not found"));

        List<Event> events = eventRepository.findAllByCalendarAndDate(calendar, date);

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
        dto.setCalendarId(event.getCalendar().getCalendarId());
        return dto;
    }
}
