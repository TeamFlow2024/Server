package com.teamflow.controller;

import com.teamflow.dto.EventDto;
import com.teamflow.dto.EventResponseDto;
import com.teamflow.model.User;
import com.teamflow.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
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
}
