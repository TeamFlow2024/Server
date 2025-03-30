package com.teamflow.repository;

import com.teamflow.model.User;
import com.teamflow.model.PersonalSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PersonalScheduleRepository extends JpaRepository<PersonalSchedule, Long> {
    Optional<PersonalSchedule> findByUserId(Long userId);

}