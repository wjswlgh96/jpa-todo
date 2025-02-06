package com.example.jpa_todo.service;

import com.example.jpa_todo.dto.response.schedule.SchedulePageResponseDto;
import com.example.jpa_todo.dto.response.schedule.ScheduleResponseDto;
import com.example.jpa_todo.entity.Schedule;
import com.example.jpa_todo.entity.User;
import com.example.jpa_todo.exception.ApplicationException;
import com.example.jpa_todo.repository.ScheduleRepository;
import com.example.jpa_todo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final UserRepository userRepository;
    private final ScheduleRepository scheduleRepository;

    public ScheduleResponseDto save(Long userId, String title, String contents) {
        User findUser = userRepository.findByIdOrElseThrow(userId);
        Schedule saveSchedule = new Schedule(title, contents);

        findUser.addSchedule(saveSchedule);
        scheduleRepository.save(saveSchedule);

        return ScheduleResponseDto.toDto(saveSchedule);
    }

    public SchedulePageResponseDto<ScheduleResponseDto> findAll(Pageable pageable) {
        Page<ScheduleResponseDto> dtoPage = scheduleRepository.findAll(pageable)
                .map(ScheduleResponseDto::toDto);

        return SchedulePageResponseDto.toDto(dtoPage);
    }

    public ScheduleResponseDto findById(Long id) {
        Schedule findSchedule = scheduleRepository.findByIdOrElseThrow(id);
        return ScheduleResponseDto.toDto(findSchedule);
    }

    @Transactional
    public void updateTitleAndContents(Long id, Long sessionId, String title, String contents) {
        Schedule findSchedule = scheduleRepository.findByIdOrElseThrow(id);
        if (!findSchedule.getUser().getId().equals(sessionId)) {
            throw new ApplicationException(HttpStatus.FORBIDDEN, "스케줄 작성자만 수정이 가능합니다.");
        }

        findSchedule.updateTitleAndContents(title, contents);
    }

    public void delete(Long id, Long sessionId) {
        Schedule findSchedule = scheduleRepository.findByIdOrElseThrow(id);
        if (!findSchedule.getUser().getId().equals(sessionId)) {
            throw new ApplicationException(HttpStatus.FORBIDDEN, "스케줄 작성자만 삭제가 가능합니다.");
        }

        scheduleRepository.delete(findSchedule);
    }
}
