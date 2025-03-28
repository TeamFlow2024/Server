package com.teamflow.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "TeamMembers")
@Getter
@Setter
public class TeamMembers {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "teamId", nullable = false)
    private Team team;

    @ManyToOne
    @JoinColumn(name = "userId", nullable = false)
    private User user;

    @Column(nullable = false)
    private String role;

    @Column(nullable = false)
    private String profile;
}
