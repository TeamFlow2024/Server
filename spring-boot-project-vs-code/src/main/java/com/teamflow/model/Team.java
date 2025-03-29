package com.teamflow.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "Team")
@Getter
@Setter
public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "team_id")
    private Long teamId;

    private String teamName;
    private String teamColor;

    @ManyToOne
    @JoinColumn(name = "userId", nullable = false)
    private User user;

    @OneToOne(mappedBy = "team", cascade = CascadeType.ALL, orphanRemoval = true)
    private TeamSchedule teamSchedule;


    @OneToMany(mappedBy = "team", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference // 순환 참조 방지 (또는 @JsonIgnoreProperties 사용 가능)
    private java.util.List<TeamMembers> teamMembers = new java.util.ArrayList<>();

}
