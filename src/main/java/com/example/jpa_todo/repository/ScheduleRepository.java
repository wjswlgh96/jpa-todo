package com.example.jpa_todo.repository;

import com.example.jpa_todo.entity.Schedule;
import com.example.jpa_todo.exception.ApplicationException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    default Schedule findByIdOrElseThrow(Long id) {
        return findById(id)
                .orElseThrow(() -> new ApplicationException(HttpStatus.NOT_FOUND, "해당 아이디의 스케줄이 없습니다 id = " + id));
    }
}
