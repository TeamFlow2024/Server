package com.teamflow.repository;

import com.teamflow.model.DirectMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface DirectMessageRepository extends JpaRepository<DirectMessage, Long> {
    // 두 사용자 간 최근 메시지 20개
    List<DirectMessage> findTop20BySenderIdAndReceiverIdOrReceiverIdAndSenderIdOrderByTimestampDesc(
        String senderId, String receiverId, Long receiverId2, Long senderId2);
}
