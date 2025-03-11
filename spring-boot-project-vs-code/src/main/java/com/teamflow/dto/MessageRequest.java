package com.teamflow.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MessageRequest {
    private Long channelId;
    private Long userId;
    private String content;
    private String messageType; // text, image, file 등
}
