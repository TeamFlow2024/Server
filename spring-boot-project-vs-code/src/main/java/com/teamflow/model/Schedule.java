package com.teamflow.model;

import com.teamflow.model.ScheduleType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "Schedule")
@Getter
@Setter
public class Schedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long scheduleId;

    @Enumerated(EnumType.STRING)
    private ScheduleType type; // PERSONAL, TEAM

    @ManyToOne
    private User user; // 개인용 (팀 캘린더면 null)

    @ManyToOne
    private Team team; // 팀용 (개인 캘린더면 null)

    private String description;
}
