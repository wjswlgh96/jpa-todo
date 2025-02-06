package com.example.jpa_todo.controller;

import com.example.jpa_todo.common.Const;
import com.example.jpa_todo.dto.request.schedule.CreateScheduleRequestDto;
import com.example.jpa_todo.dto.request.schedule.UpdateScheduleTitleAndContentsRequestDto;
import com.example.jpa_todo.dto.response.schedule.SchedulePageResponseDto;
import com.example.jpa_todo.dto.response.schedule.ScheduleResponseDto;
import com.example.jpa_todo.dto.response.user.UserResponseDto;
import com.example.jpa_todo.service.ScheduleService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/schedules")
@RequiredArgsConstructor
public class ScheduleController {

    private final ScheduleService scheduleService;

    @PostMapping
    public ResponseEntity<ScheduleResponseDto> save(
            @Valid @RequestBody CreateScheduleRequestDto requestDto,
            HttpServletRequest request
    ) {
        HttpSession session = request.getSession(false);
        UserResponseDto sessionUser = (UserResponseDto) session.getAttribute(Const.LOGIN_USER);
        ScheduleResponseDto responseDto = scheduleService.save(sessionUser.getId(), requestDto.getTitle(), requestDto.getContents());
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    @GetMapping
    public ResponseEntity<SchedulePageResponseDto<ScheduleResponseDto>> findAll(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "modifiedAt") String sort
    ) {
        Pageable pageable = PageRequest.of(page - 1, size, Sort.by(sort).descending());
        SchedulePageResponseDto<ScheduleResponseDto> responseDto = scheduleService.findAll(pageable);
        return ResponseEntity.ok(responseDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ScheduleResponseDto> findById(@PathVariable Long id) {
        ScheduleResponseDto responseDto = scheduleService.findById(id);
        return ResponseEntity.ok(responseDto);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> updateTitleAndContents(
            @PathVariable Long id,
            @Valid @RequestBody UpdateScheduleTitleAndContentsRequestDto requestDto,
            HttpServletRequest request
    ) {
        HttpSession session = request.getSession(false);
        UserResponseDto sessionUser = (UserResponseDto) session.getAttribute(Const.LOGIN_USER);

        scheduleService.updateTitleAndContents(id, sessionUser.getId(), requestDto.getTitle(), requestDto.getContents());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable Long id,
            HttpServletRequest request
    ) {
        HttpSession session = request.getSession(false);
        UserResponseDto sessionUser = (UserResponseDto) session.getAttribute(Const.LOGIN_USER);

        scheduleService.delete(id, sessionUser.getId());
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
