package com.teamflow.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignalMessage {
    private String type;         // "offer", "answer", "candidate"
    private String senderId;     // 보낸 사람 ID
    private String receiverId;   // 받는 사람 ID
    private String roomId;       // 방 ID (예: teamId 기반)
    private Object data;         // SDP나 ICE candidate 정보
}
