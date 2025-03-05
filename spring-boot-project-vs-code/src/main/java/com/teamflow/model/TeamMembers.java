package com.teamflow.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "TeamMembers")
@Getter
@Setter
public class TeamMembers {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "teamId", nullable = false)
    private Team team;

    @ManyToOne
    @JoinColumn(name = "userId", nullable = false)
    private User user;

    private String role;
    private String memberColor;
}
