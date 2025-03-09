package com.teamflow.controller;

import com.teamflow.dto.EventDto;
import com.teamflow.dto.EventResponseDto;
import com.teamflow.model.User;
import com.teamflow.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/events")
@RequiredArgsConstructor
public class EventController {

    private final EventService eventService;

    @PostMapping("/{calendarId}")
    public EventResponseDto createEvent(@PathVariable Long calendarId, @RequestBody EventDto dto) {
        return eventService.addEvent(calendarId, dto);
    }

    @GetMapping
    public List<EventResponseDto> getEvents(@AuthenticationPrincipal User user) {
        return eventService.getEventsForUser(user);
    }

    @PatchMapping("/{eventId}")
    public EventResponseDto updateEvent(@PathVariable Long eventId, @RequestBody EventDto dto) {
        return eventService.updateEvent(eventId, dto);
    }

    @GetMapping("/calendar/{calendarId}")
    public List<EventResponseDto> getAllEventsByCalendar(@PathVariable Long calendarId) {
        return eventService.getAllEventsByCalendar(calendarId);
    }

    // 🔥 추가된 코드 (캘린더 아이디 + 날짜로 조회)
    @GetMapping("/{calendarId}/{date}")
    public List<EventResponseDto> getEventsByDateAndCalendar(
            @PathVariable Long calendarId,
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return eventService.getEventsByCalendarAndDate(calendarId, date);
    }
}
