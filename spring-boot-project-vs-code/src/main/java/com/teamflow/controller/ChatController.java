package com.teamflow.controller;

import com.teamflow.dto.ChatMessage;
import com.teamflow.model.*;
import com.teamflow.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.*;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.time.LocalDateTime;

@Controller
@RequiredArgsConstructor
public class ChatController {

    private final DirectMessageRepository directMessageRepository;
    private final TeamMessageRepository teamMessageRepository;
    private final SimpMessagingTemplate messagingTemplate;

    // 개인채팅 메시지 전송
    @MessageMapping("/dm.sendMessage")
    public void sendDirectMessage(@Payload ChatMessage chatMessage) {

        DirectMessage dm = DirectMessage.builder()
                .senderId(chatMessage.getSenderId())
                .receiverId(chatMessage.getReceiverId())
                .content(chatMessage.getContent())
                .timestamp(LocalDateTime.now())
                .build();

        directMessageRepository.save(dm);

        // 수신자에게만 개인적으로 전달
        messagingTemplate.convertAndSendToUser(
            String.valueOf(chatMessage.getReceiverId()),
            "/queue/private",
            chatMessage
        );
    }

    // 팀 단체 채팅 메시지 전송
    @MessageMapping("/team.sendMessage/{teamId}")
    @SendTo("/topic/team/{teamId}")
    public ChatMessage sendTeamMessage(@DestinationVariable Long teamId, @Payload ChatMessage chatMessage) {

        TeamMessage tm = TeamMessage.builder()
                .teamId(teamId)
                .senderId(chatMessage.getSenderId())
                .content(chatMessage.getContent())
                .timestamp(LocalDateTime.now())
                .build();

        teamMessageRepository.save(tm);

        // 팀 전체에게 전달
        return chatMessage;
    }
}

