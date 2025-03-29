// package com.teamflow.controller;

// import com.teamflow.dto.ScheduleDto;
// import com.teamflow.model.Schedule;
// import com.teamflow.model.User;
// import com.teamflow.service.ScheduleService;
// import lombok.RequiredArgsConstructor;
// import org.springframework.security.core.annotation.AuthenticationPrincipal;
// import org.springframework.web.bind.annotation.*;

// import java.util.List;

// @RestController
// @RequestMapping("/api/Schedule")
// @RequiredArgsConstructor
// public class ScheduleController {

//     private final ScheduleService scheduleService;

//     @GetMapping
//     public List<Schedule> getSchedules(@AuthenticationPrincipal User user) {
//         return scheduleService.getSchedulesForUser(user);
//     }
// }
