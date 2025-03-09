package com.teamflow.repository;

import com.teamflow.model.Team;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeamRepository extends JpaRepository<Team, Long> {
    List<Team> findAllByTeamNameIn(List<String> teamNames);

}
