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

    // ✅ 개인 캘린더에 이벤트 추가
    @PostMapping("/personal")
    public EventResponseDto createPersonalEvent(@RequestBody EventDto dto,
                                                @AuthenticationPrincipal User user) {
        return eventService.addEventToPersonalSchedule(dto, user);
    }

    // ✅ 팀 캘린더에 이벤트 추가
    @PostMapping("/team")
    public EventResponseDto createTeamEvent(@RequestBody EventDto dto) {
        return eventService.addEventToTeamSchedule(dto);
    }

    // ✅ 내 모든 이벤트 조회 (개인 + 팀 통합)
    @GetMapping("/personal")
    public List<EventResponseDto> getMyEvents(@AuthenticationPrincipal User user) {
        return eventService.getEventsForUser(user);
    }

    // ✅ 팀 일정 조회
    @GetMapping("/team/{teamId}")
    public List<EventResponseDto> getTeamEvents(@PathVariable Long teamId) {
        return eventService.getEventsByTeam(teamId);
    }

    // ✅ 단일 스케줄에 모든 이벤트 조회 (팀 일정만 사용됨)
    @GetMapping("/schedule/{scheduleId}")
    public List<EventResponseDto> getAllEventsBySchedule(@PathVariable Long scheduleId) {
        return eventService.getAllEventsBySchedule(scheduleId);
    }

    // ✅ 날짜 + 스케줄 ID로 조회
    @GetMapping("/{scheduleId}/{date}")
    public List<EventResponseDto> getEventsByDateAndSchedule(
            @PathVariable Long scheduleId,
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return eventService.getEventsByScheduleAndDate(scheduleId, date);
    }

    // ✅ 이벤트 수정
    @PatchMapping("/{eventId}")
    public EventResponseDto updateEvent(@PathVariable Long eventId, @RequestBody EventDto dto) {
        return eventService.updateEvent(eventId, dto);
    }
} 