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
public List<DirectMessage> getAllDirectMessagesNoTeam(
        @PathVariable String senderId,
        @PathVariable String receiverId) {

    return directMessageRepository
            .findAllBySenderIdAndReceiverIdOrReceiverIdAndSenderIdOrderByTimestampDesc(
                    senderId, receiverId,
                    receiverId, senderId
            );
}

    // ✅ 팀 내 두 유저 간 전체 메시지 조회
    @GetMapping("/dm/{teamId}/{senderId}/{receiverId}/all")
    public List<DirectMessage> getAllDirectMessages(
            @PathVariable Long teamId,
            @PathVariable String senderId,
            @PathVariable String receiverId) {

        return directMessageRepository
                .findAllByTeamIdAndSenderIdAndReceiverIdOrTeamIdAndReceiverIdAndSenderIdOrderByTimestampDesc(
                        teamId, senderId, receiverId,
                        teamId, receiverId, senderId
                );
    }

        @GetMapping("/team/{teamId}")
        public List<DirectMessage> getAllTeamMessages(@PathVariable Long teamId) {
        return directMessageRepository.findAllByTeamIdOrderByTimestampAsc(teamId);
        }


}
