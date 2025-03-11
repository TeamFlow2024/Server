package com.teamflow.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "channels")
@Getter
@Setter
@NoArgsConstructor
public class Channel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String channelName;
    private String channelType; // "team" 또는 "direct"

    @ManyToOne
    @JoinColumn(name = "team_id", referencedColumnName = "team_id") // ✅ teamid가 올바르게 연결되었는지 확인
    private Team team;
}
