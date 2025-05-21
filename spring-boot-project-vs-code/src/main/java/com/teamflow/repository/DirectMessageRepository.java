package com.teamflow.repository;

import com.teamflow.model.DirectMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface DirectMessageRepository extends JpaRepository<DirectMessage, Long> {
    // 두 사용자 간 최근 메시지 20개
    List<DirectMessage> findAllByTeamIdAndSenderIdAndReceiverIdOrTeamIdAndReceiverIdAndSenderIdOrderByTimestampDesc(
    Long teamId1, String senderId1, String receiverId1,
    Long teamId2, String receiverId2, String senderId2
    );

    List<DirectMessage> findAllBySenderIdAndReceiverIdOrReceiverIdAndSenderIdOrderByTimestampDesc(
    String senderId1, String receiverId1,
    String receiverId2, String senderId2
    );

    // ✅ 1. 팀 내에서 나와 상대방이 주고받은 DM 전체 조회 (상대방 리스트 뽑을 때 사용)
    List<DirectMessage> findAllByTeamIdAndSenderIdOrTeamIdAndReceiverId(
        Long teamId1, String senderId1,
        Long teamId2, String receiverId2
    );

    // ✅ 2. 팀 + 두 사용자 간 가장 최근 메시지 1개 (채팅 목록 마지막 메시지용)
    DirectMessage findTop1ByTeamIdAndSenderIdAndReceiverIdOrTeamIdAndReceiverIdAndSenderIdOrderByTimestampDesc(
        Long teamId1, String senderId1, String receiverId1,
        Long teamId2, String receiverId2, String senderId2
    );

    // ✅ 3. 팀 + 두 사용자 간 최근 20개 (채팅방 입장 후 메시지 내역)
    List<DirectMessage> findTop20ByTeamIdAndSenderIdAndReceiverIdOrTeamIdAndReceiverIdAndSenderIdOrderByTimestampDesc(
        Long teamId1, String senderId1, String receiverId1,
        Long teamId2, String receiverId2, String senderId2
    );

    List<DirectMessage> findAllByTeamIdOrderByTimestampAsc(Long teamId);

}
