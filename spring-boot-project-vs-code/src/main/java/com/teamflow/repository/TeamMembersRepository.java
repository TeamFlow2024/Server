package com.teamflow.repository;

import com.teamflow.model.TeamMembers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeamMembersRepository extends JpaRepository<TeamMembers, Long> {
    boolean existsByTeam_TeamIdAndUser_UserId(Long teamId, String userId);
}
