package com.teamflow.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "Event")
@Getter
@Setter
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long eventId;

    @ManyToOne
    private Schedule schedule;

    private String title;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String color;
}
