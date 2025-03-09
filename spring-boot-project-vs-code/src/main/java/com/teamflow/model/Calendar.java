package com.teamflow.model;

import com.teamflow.model.CalendarType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "Calendar")
@Getter
@Setter
public class Calendar {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long calendarId;

    @Enumerated(EnumType.STRING)
    private CalendarType type; // PERSONAL, TEAM

    @ManyToOne
    private User user; // 개인용 (팀 캘린더면 null)

    @ManyToOne
    private Team team; // 팀용 (개인 캘린더면 null)

    private String description;
}
