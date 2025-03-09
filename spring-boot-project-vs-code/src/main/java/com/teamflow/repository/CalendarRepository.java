package com.teamflow.repository;

import com.teamflow.model.Calendar;
import com.teamflow.model.Team;
import com.teamflow.model.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CalendarRepository extends JpaRepository<Calendar, Long> {
    Optional<Calendar> findByUser(User user);

    List<Calendar> findAllByTeamIn(List<Team> teams);
}
