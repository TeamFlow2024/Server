package com.teamflow.repository;

import com.teamflow.model.Calendar;
import com.teamflow.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long> {
    List<Event> findAllByCalendar(Calendar calendar);

    List<Event> findAllByCalendarIn(List<Calendar> calendars);

    // 🔥 추가된 코드 (특정 날짜에 해당하는 이벤트 조회)
    @Query("SELECT e FROM Event e WHERE e.calendar = :calendar AND DATE(e.startTime) <= :date AND DATE(e.endTime) >= :date")
    List<Event> findAllByCalendarAndDate(@Param("calendar") Calendar calendar, @Param("date") LocalDate date);
}
