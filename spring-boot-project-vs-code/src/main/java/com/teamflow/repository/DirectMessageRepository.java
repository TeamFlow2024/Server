package com.teamflow.repository;

import com.teamflow.model.DirectMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DirectMessageRepository extends JpaRepository<DirectMessage, Long> {

    // ✅ 1. 팀 + 두 사용자 간 최근 20개 (채팅방 입장 시)
    @Query("SELECT d FROM DirectMessage d " +
           "WHERE d.team.teamId = :teamId AND " +
           "((d.senderId = :senderId AND d.receiverId = :receiverId) " +
           "OR (d.senderId = :receiverId AND d.receiverId = :senderId)) " +
           "ORDER BY d.timestamp DESC")
    List<DirectMessage> findRecentMessagesInTeam(
        @Param("teamId") Long teamId,
        @Param("senderId") String senderId,
        @Param("receiverId") String receiverId
    );

    // ✅ 2. 팀 + 두 사용자 간 가장 최근 메시지 1개 (채팅 목록용)
    @Query("SELECT d FROM DirectMessage d " +
           "WHERE d.team.teamId = :teamId AND " +
           "((d.senderId = :senderId AND d.receiverId = :receiverId) " +
           "OR (d.senderId = :receiverId AND d.receiverId = :senderId)) " +
           "ORDER BY d.timestamp DESC")
    DirectMessage findLastMessageInTeam(
        @Param("teamId") Long teamId,
        @Param("senderId") String senderId,
        @Param("receiverId") String receiverId
    );

    // ✅ 3. 팀 내 특정 사용자 기준으로 받은/보낸 메시지 전체 (상대방 리스트 뽑기용)
    @Query("SELECT d FROM DirectMessage d " +
           "WHERE d.team.teamId = :teamId AND (d.senderId = :userId OR d.receiverId = :userId)")
    List<DirectMessage> findAllMessagesInTeamByUser(
        @Param("teamId") Long teamId,
        @Param("userId") String userId
    );

    // ✅ 4. 개인 쪽지 (팀 없이, sender-receiver 쌍)
    @Query("SELECT d FROM DirectMessage d " +
           "WHERE (d.senderId = :senderId AND d.receiverId = :receiverId) " +
           "OR (d.senderId = :receiverId AND d.receiverId = :senderId) " +
           "ORDER BY d.timestamp DESC")
    List<DirectMessage> findPrivateMessages(
        @Param("senderId") String senderId,
        @Param("receiverId") String receiverId
    );
}
