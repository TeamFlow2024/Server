package com.teamflow.controller;

import com.teamflow.dto.EventResponseDto;
import com.teamflow.dto.PersonalEventDto;
import com.teamflow.dto.TeamEventDto;
import com.teamflow.model.User;
import com.teamflow.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.format.annotation.DateTimeFormat;
import jakarta.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/events")
@RequiredArgsConstructor
public class EventController {

    private final EventService eventService;

    // 개인 일정 추가
        @PostMapping("/personal")
    public EventResponseDto createPersonalEvent(@RequestBody PersonalEventDto dto,
                                                HttpServletRequest request) {
        return eventService.addEventToPersonalSchedule(dto, request);
    }

    // 팀 일정 추가
    @PostMapping("/team")
    public EventResponseDto createTeamEvent(@RequestBody TeamEventDto dto) {
        return eventService.addEventToTeamSchedule(dto);
    }


    // ✅ 개인 일정만
    @GetMapping("/personal")
    public List<EventResponseDto> getMyEvents(HttpServletRequest request) {
        return eventService.getPersonalEvents(request);
    }
    

    // ✅ 팀 일정만
    @GetMapping("/team/{teamId}")
    public List<EventResponseDto> getTeamEvents(@PathVariable Long teamId) {
        return eventService.getEventsByTeam(teamId);
    }

    // ✅ 개인 + 소속된 팀 전체 일정
    @GetMapping("/all")
    public List<EventResponseDto> getAllMyEvents(@AuthenticationPrincipal User user) {
        if (user == null) {
            System.out.println("❌ @AuthenticationPrincipal User is null!");
            throw new RuntimeException("인증된 사용자 정보가 없습니다.");
        }
        System.out.println("✅ 전체 일정 요청 유저: " + user.getUserId());
        return eventService.getEventsForUser(user);
    }


    // ✅ 단일 스케줄에 모든 이벤트 조회 (팀 일정만 사용됨)
    // @GetMapping("/schedule/{scheduleId}")
    // public List<EventResponseDto> getAllEventsBySchedule(@PathVariable Long scheduleId) {
    //     return eventService.getAllEventsBySchedule(scheduleId);
    // }

    // ✅ 날짜 + 스케줄 ID로 조회
    @GetMapping("/{scheduleId}/{date}")
    public List<EventResponseDto> getEventsByDateAndSchedule(
            @PathVariable Long scheduleId,
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return eventService.getEventsByScheduleAndDate(scheduleId, date);
    }

} 