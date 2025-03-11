package com.teamflow.repository;

import com.teamflow.model.Schedule;
import com.teamflow.model.Team;
import com.teamflow.model.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    Optional<Schedule> findByUser(User user);

    List<Schedule> findAllByTeamIn(List<Team> teams);
}
