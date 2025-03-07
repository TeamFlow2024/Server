package com.teamflow.repository;

import com.teamflow.model.Calendar;
import com.teamflow.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long> {
    List<Event> findAllByCalendar(Calendar calendar);

    List<Event> findAllByCalendarIn(List<Calendar> calendars);
}
