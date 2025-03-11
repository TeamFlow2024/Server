package com.teamflow.service;

import com.teamflow.model.Channel;
import com.teamflow.model.Message;
import com.teamflow.model.User;
import com.teamflow.repository.ChannelRepository;
import com.teamflow.repository.MessageRepository;
import com.teamflow.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class MessageService {
    private final MessageRepository messageRepository;
    private final ChannelRepository channelRepository;
    private final UserRepository userRepository;

    public Message sendMessage(Long channelId, Long userId, String content, String type) {
        Channel channel = channelRepository.findById(channelId)
                .orElseThrow(() -> new RuntimeException("채널을 찾을 수 없습니다."));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));

        Message message = new Message();
        message.setChannel(channel);
        message.setUser(user);
        message.setContent(content);
        message.setMessageType(type);
        message.setTimestamp(LocalDateTime.now());

        return messageRepository.save(message);
    }

    public List<Message> getMessages(Long channelId) {
        return messageRepository.findByChannelIdOrderByTimestampAsc(channelId);
    }
}
