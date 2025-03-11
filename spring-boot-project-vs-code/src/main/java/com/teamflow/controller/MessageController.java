package com.teamflow.controller;

import com.teamflow.dto.MessageRequest;
import com.teamflow.model.Message;
import com.teamflow.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/messages")
@RequiredArgsConstructor
public class MessageController {
    private final MessageService messageService;

    @PostMapping
    public ResponseEntity<Message> sendMessage(@RequestBody MessageRequest request) {
        Message message = messageService.sendMessage(
                request.getChannelId(),
                request.getUserId(),
                request.getContent(),
                request.getMessageType());
        return ResponseEntity.ok(message);
    }

    @GetMapping("/{channelId}")
    public ResponseEntity<List<Message>> getMessages(@PathVariable Long channelId) {
        return ResponseEntity.ok(messageService.getMessages(channelId));
    }
}
