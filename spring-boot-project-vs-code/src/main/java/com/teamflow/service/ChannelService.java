package com.teamflow.service;

import com.teamflow.model.Channel;
import com.teamflow.model.Team;
import com.teamflow.model.User;
import com.teamflow.repository.ChannelRepository;
import com.teamflow.repository.TeamRepository;
import com.teamflow.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ChannelService {
    private final ChannelRepository channelRepository;
    private final TeamRepository teamRepository;
    private final UserRepository userRepository;

    public Channel createChannel(String type, Long teamId, Long userId1, Long userId2) {
        Channel channel = new Channel();
        channel.setChannelType(type);

        if ("team".equals(type)) {
            Team team = teamRepository.findById(teamId)
                    .orElseThrow(() -> new RuntimeException("팀을 찾을 수 없습니다."));
            channel.setChannelName(team.getTeamName()); // 팀 채널이면 팀 이름 사용
            channel.setTeam(team);
        } else if ("direct".equals(type)) {
            User user1 = userRepository.findById(userId1)
                    .orElseThrow(() -> new RuntimeException("첫 번째 사용자를 찾을 수 없습니다."));
            User user2 = userRepository.findById(userId2)
                    .orElseThrow(() -> new RuntimeException("두 번째 사용자를 찾을 수 없습니다."));

            // 다이렉트 채널이면 상대방 이름을 채널 이름으로 설정
            channel.setChannelName(user2.getUsername());
        } else {
            throw new IllegalArgumentException("올바른 채널 타입이 아닙니다. (team 또는 direct)");
        }

        return channelRepository.save(channel);
    }
}
