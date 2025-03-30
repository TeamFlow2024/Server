package com.teamflow.dto;

import lombok.*;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor @Builder
public class ChatMessage {
    private Long senderId;
    private Long receiverId; // 개인채팅 시 수신자ID
    private Long teamId;     // 팀채팅 시 팀ID
    private String senderName;
    private String content;
}
