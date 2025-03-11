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

    @PostMapping("/{scheduleId}")
    public EventResponseDto createEvent(@PathVariable Long scheduleId, @RequestBody EventDto dto) {
        return eventService.addEvent(scheduleId, dto);
    }

    @GetMapping
    public List<EventResponseDto> getEvents(@AuthenticationPrincipal User user) {
        return eventService.getEventsForUser(user);
    }

    @PatchMapping("/{eventId}")
    public EventResponseDto updateEvent(@PathVariable Long eventId, @RequestBody EventDto dto) {
        return eventService.updateEvent(eventId, dto);
    }

    @GetMapping("/schedule/{scheduleId}")
    public List<EventResponseDto> getAllEventsBySchedule(@PathVariable Long scheduleId) {
        return eventService.getAllEventsBySchedule(scheduleId);
    }

    // 🔥 추가된 코드 (캘린더 아이디 + 날짜로 조회)
    @GetMapping("/{scheduleId}/{date}")
    public List<EventResponseDto> getEventsByDateAndSchedule(
            @PathVariable Long scheduleId,
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return eventService.getEventsByScheduleAndDate(scheduleId, date);
    }
}
