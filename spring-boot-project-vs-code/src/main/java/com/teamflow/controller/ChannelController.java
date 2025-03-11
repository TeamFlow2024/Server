package com.teamflow.controller;

import com.teamflow.dto.ChannelRequest;
import com.teamflow.model.Channel;
import com.teamflow.service.ChannelService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/channels")
@RequiredArgsConstructor
public class ChannelController {
    private final ChannelService channelService;

    @PostMapping
    public ResponseEntity<Channel> createChannel(@RequestBody ChannelRequest request) {
        Channel channel = channelService.createChannel(
                request.getChannelType(),
                request.getTeamId(),
                request.getUserId1(),
                request.getUserId2());
        return ResponseEntity.ok(channel);
    }
}
