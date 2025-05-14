package com.teamflow.controller;

import com.teamflow.model.DirectMessage;
import com.teamflow.repository.DirectMessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/chat")
public class ChatRoomController {

    private final DirectMessageRepository directMessageRepository;
    // 깃 업데이트

    // ✅ 사용자 기준 DM 채팅 상대 목록 조회 임
    @GetMapping("/dm/users/{userId}")
    public Set<Long> getDMUserList(@PathVariable Long userId) {
        List<DirectMessage> messages = directMessageRepository.findAll();

        // 내가 참여한 DM에서 상대방 ID만 추출
        return messages.stream()
                .filter(m -> Objects.equals(m.getSenderId(), userId) || Objects.equals(m.getReceiverId(), userId))
                .map(m -> Objects.equals(m.getSenderId(), userId) ? m.getReceiverId() : m.getSenderId())
                .collect(Collectors.toSet());
    }
}
