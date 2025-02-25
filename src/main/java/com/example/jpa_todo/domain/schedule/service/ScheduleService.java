package com.example.jpa_todo.domain.schedule.service;

import com.example.jpa_todo.domain.user.service.UserService;
import com.example.jpa_todo.domain.schedule.dto.response.SchedulePageResponseDto;
import com.example.jpa_todo.domain.schedule.dto.response.ScheduleResponseDto;
import com.example.jpa_todo.domain.schedule.entity.Schedule;
import com.example.jpa_todo.domain.user.entity.User;
import com.example.jpa_todo.common.exception.ApplicationException;
import com.example.jpa_todo.domain.schedule.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;

    private final UserService userService;

    @Transactional
    public ScheduleResponseDto createSchedule(Long userId, String title, String contents) {
        User findUser = userService.findByIdOrElseThrow(userId);
        Schedule saveSchedule = new Schedule(title, contents);

        findUser.addSchedule(saveSchedule);
        scheduleRepository.save(saveSchedule);

        return ScheduleResponseDto.toDto(saveSchedule);
    }

    public SchedulePageResponseDto<ScheduleResponseDto> getSchedules(Pageable pageable) {
        Page<ScheduleResponseDto> dtoPage = scheduleRepository.findAll(pageable)
                .map(ScheduleResponseDto::toDto);

        return SchedulePageResponseDto.toDto(dtoPage);
    }

    public ScheduleResponseDto getScheduleById(Long id) {
        Schedule findSchedule = findByIdOrElseThrow(id);
        return ScheduleResponseDto.toDto(findSchedule);
    }

    @Transactional
    public void updateTitleAndContents(Long id, Long sessionId, String title, String contents) {
        Schedule findSchedule = findByIdOrElseThrow(id);
        if (!findSchedule.getUser().getId().equals(sessionId)) {
            throw new ApplicationException(HttpStatus.FORBIDDEN, "스케줄 작성자만 수정이 가능합니다.");
        }

        findSchedule.updateTitleAndContents(title, contents);
    }

    @Transactional
    public void deleteSchedule(Long id, Long sessionId) {
        Schedule findSchedule = findByIdOrElseThrow(id);
        if (!findSchedule.getUser().getId().equals(sessionId)) {
            throw new ApplicationException(HttpStatus.FORBIDDEN, "스케줄 작성자만 삭제가 가능합니다.");
        }

        scheduleRepository.delete(findSchedule);
    }

    public Schedule findByIdOrElseThrow(Long id) {
        return scheduleRepository.findById(id)
                .orElseThrow(() -> new ApplicationException(HttpStatus.NOT_FOUND, "해당 아이디의 스케줄이 없습니다 id = " + id));
    }
}
