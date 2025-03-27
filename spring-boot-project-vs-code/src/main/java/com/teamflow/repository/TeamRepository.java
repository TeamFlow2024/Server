package com.teamflow.repository;

import com.teamflow.model.Team;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

@Repository
public interface TeamRepository extends JpaRepository<Team, Long> {
    List<Team> findAllByTeamNameIn(List<String> teamNames);

    @Query("SELECT t FROM Team t LEFT JOIN FETCH t.teamMembers tm LEFT JOIN FETCH tm.user WHERE t.teamId = :teamId")
    Optional<Team> findByIdWithMembers(@Param("teamId") Long teamId);


}
