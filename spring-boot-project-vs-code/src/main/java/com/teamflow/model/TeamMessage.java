package com.teamflow.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Getter @Setter @Builder
@AllArgsConstructor @NoArgsConstructor
public class TeamMessage {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long teamId;
    private String senderId;
    private String content;
    private LocalDateTime timestamp;
}
