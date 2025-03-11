package com.teamflow.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import com.teamflow.model.User;
import com.teamflow.model.Channel;
import java.time.LocalDateTime;

@Entity
@Table(name = "messages")
@Getter
@Setter
@NoArgsConstructor
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "channelid")
    private Channel channel;

    @ManyToOne
    @JoinColumn(name = "userid")
    private User user;

    private String content;
    private String messageType; // "text", "image", "file" 등
    private LocalDateTime timestamp;
}
