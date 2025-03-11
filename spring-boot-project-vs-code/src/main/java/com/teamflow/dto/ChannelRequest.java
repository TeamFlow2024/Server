package com.teamflow.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChannelRequest {
    private String channelType; // "team" 또는 "direct"
    private Long teamId; // 팀 채널이면 필요
    private Long userId1; // 다이렉트 채팅을 시작하는 사용자
    private Long userId2; // 다이렉트 채팅 상대방
}
