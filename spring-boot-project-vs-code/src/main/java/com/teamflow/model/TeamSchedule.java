package com.teamflow.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class TeamSchedule {

    @Id
    private Long teamId; // ✅ teamId = scheduleId

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "team_id")
    private Team team;

    private String description;
}
