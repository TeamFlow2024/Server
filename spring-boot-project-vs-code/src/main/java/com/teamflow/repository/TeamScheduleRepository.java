package com.teamflow.repository;

import com.teamflow.model.Team;
import com.teamflow.model.TeamSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TeamScheduleRepository extends JpaRepository<TeamSchedule, Long> {
    Optional<TeamSchedule> findByTeam(Team team);
}