package com.teamflow.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "Event")
@Getter
@Setter
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long eventId;

    @ManyToOne
    private TeamSchedule teamSchedule;

    @ManyToOne
    private PersonalSchedule personalSchedule;

    private String title;
    private LocalDate startTime;
    private LocalDate endTime;
    private String color;
}
