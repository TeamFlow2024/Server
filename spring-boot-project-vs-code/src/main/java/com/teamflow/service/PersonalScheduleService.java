package com.teamflow.service;

import com.teamflow.model.PersonalSchedule;
import com.teamflow.model.User;
import com.teamflow.repository.PersonalScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PersonalScheduleService {

    private final PersonalScheduleRepository personalScheduleRepository;

    public PersonalSchedule createForUser(User user) {
        PersonalSchedule ps = new PersonalSchedule();
        ps.setUser(user);
        ps.setDescription(user.getUsername() + "의 개인 캘린더");
        return personalScheduleRepository.save(ps);
    }
}