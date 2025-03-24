package com.teamflow.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

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

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "schedule_id")
    private com.teamflow.model.Schedule schedule;

    @OneToMany(mappedBy = "team", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference // 순환 참조 방지 (또는 @JsonIgnoreProperties 사용 가능)
    private java.util.List<TeamMembers> teamMembers = new java.util.ArrayList<>();

}
