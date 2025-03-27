package com.teamflow.repository;

import com.teamflow.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

// User 엔터티를 위한 Repository 인터페이스
public interface UserRepository extends JpaRepository<User, Long> {
    // 특정 username을 가진 User 찾기
    Optional<User> findByUsername(String username);

    @EntityGraph(attributePaths = "teamMembers.team")
    Optional<User> findByUserId(String userId);

    Optional<User> findByUserId(String userId);

    // 이메일로 사용자 찾기 (비밀번호 찾기 등에 활용 가능)
    Optional<User> findByEmail(String email);
}
