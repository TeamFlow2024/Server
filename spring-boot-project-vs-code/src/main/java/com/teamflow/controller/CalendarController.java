package com.teamflow.controller;

import com.teamflow.dto.CalendarDto;
import com.teamflow.model.Calendar;
import com.teamflow.model.User;
import com.teamflow.service.CalendarService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/calendars")
@RequiredArgsConstructor
public class CalendarController {

    private final CalendarService calendarService;

    @GetMapping
    public List<Calendar> getCalendars(@AuthenticationPrincipal User user) {
        return calendarService.getCalendarsForUser(user);
    }
}
