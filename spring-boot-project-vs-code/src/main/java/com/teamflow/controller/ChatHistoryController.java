package com.teamflow.controller;

import com.teamflow.model.DirectMessage;
import com.teamflow.model.TeamMessage;
import com.teamflow.repository.DirectMessageRepository;
import com.teamflow.repository.TeamMessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/chat")
public class ChatHistoryController {

    private final DirectMessageRepository directMessageRepository;
    private final TeamMessageRepository teamMessageRepository;

    // ✅ 개인 채팅 기록 조회
    @GetMapping("/dm/{senderId}/{receiverId}")
    public List<DirectMessage> getDirectMessages(
            @PathVariable String senderId,
            @PathVariable String receiverId) {
        return directMessageRepository
                .findTop20BySenderIdAndReceiverIdOrReceiverIdAndSenderIdOrderByTimestampDesc(
                        senderId, receiverId, receiverId, senderId);
    }

    // ✅ 팀 채팅 기록 조회
    @GetMapping("/team/{teamId}")
    public List<TeamMessage> getTeamMessages(@PathVariable Long teamId) {
        return teamMessageRepository
                .findTop20ByTeamIdOrderByTimestampDesc(teamId);
    }
}
