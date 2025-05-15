package com.teamflow.controller;

import com.teamflow.dto.SignalMessage;
import org.springframework.messaging.handler.annotation.*;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;

@Controller
public class SignalController {

    @MessageMapping("/signal/{roomId}")   // 클라이언트 전송: /app/signal/{roomId}
    @SendTo("/topic/signal/{roomId}")     // 서버 브로드캐스트: /topic/signal/{roomId}
    public SignalMessage handleSignal(@Payload SignalMessage message,
                                      @DestinationVariable String roomId) {
        System.out.println("📡 시그널링 메시지 도착: [" + message.getType() + "] from " + message.getSenderId() + " to " + message.getReceiverId());
        return message;
    }
}
