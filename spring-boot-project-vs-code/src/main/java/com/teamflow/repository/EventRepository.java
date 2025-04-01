package com.teamflow.repository;

import com.teamflow.model.Event;
import com.teamflow.model.TeamSchedule;
import com.teamflow.model.PersonalSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {

    List<Event> findByTeamSchedule(TeamSchedule teamSchedule);
    
    // ✅ 팀 일정 조회
    List<Event> findAllByTeamSchedule(TeamSchedule teamSchedule);

    // ✅ 개인 일정 조회
    List<Event> findAllByPersonalSchedule(PersonalSchedule personalSchedule);

    // ✅ 날짜 기준 조회 (개인)
    @Query("SELECT e FROM Event e WHERE e.personalSchedule = :schedule AND e.startTime <= :date AND e.endTime >= :date")
    List<Event> findAllByPersonalScheduleAndDate(@Param("schedule") PersonalSchedule schedule,
                                                 @Param("date") LocalDate date);

    // ✅ 날짜 기준 조회 (팀)
    @Query("SELECT e FROM Event e WHERE e.teamSchedule = :schedule AND e.startTime <= :date AND e.endTime >= :date")
    List<Event> findAllByTeamScheduleAndDate(@Param("schedule") TeamSchedule schedule,
                                             @Param("date") LocalDate date);
}
