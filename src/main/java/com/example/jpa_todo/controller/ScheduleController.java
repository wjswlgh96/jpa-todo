package com.example.jpa_todo.controller;

import com.example.jpa_todo.common.SessionUtil;
import com.example.jpa_todo.dto.request.schedule.CreateScheduleRequestDto;
import com.example.jpa_todo.dto.request.schedule.UpdateScheduleTitleAndContentsRequestDto;
import com.example.jpa_todo.dto.response.schedule.SchedulePageResponseDto;
import com.example.jpa_todo.dto.response.schedule.ScheduleResponseDto;
import com.example.jpa_todo.dto.response.user.UserResponseDto;
import com.example.jpa_todo.service.ScheduleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/schedules")
@RequiredArgsConstructor
@Tag(name = "Schedule API", description = "일정 관련 API")
public class ScheduleController {

    private final ScheduleService scheduleService;

    @PostMapping
    @Operation(summary = "할일 등록", description = "현재 로그인되어 있는 유저의 ID로 할일을 새로 생성합니다.")
    public ResponseEntity<ScheduleResponseDto> save(
            @Valid @RequestBody CreateScheduleRequestDto requestDto,
            HttpServletRequest request
    ) {
        UserResponseDto sessionUser = SessionUtil.getSessionUser(request);
        ScheduleResponseDto responseDto = scheduleService.save(sessionUser.getId(), requestDto.getTitle(), requestDto.getContents());
        return ResponseEntity.ok(responseDto);
    }

    @GetMapping
    @Operation(summary = "모든 할일 조회", description = "현재 등록되어 있는 모든 할일 목록을 조회합니다. 페이지네이션 형식입니다.")
    public ResponseEntity<SchedulePageResponseDto<ScheduleResponseDto>> findAll(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "modifiedAt") String sort
    ) {
        Pageable pageable = PageRequest.of(Math.max(0, page - 1), size, Sort.by(sort).descending());
        SchedulePageResponseDto<ScheduleResponseDto> responseDto = scheduleService.findAll(pageable);
        return ResponseEntity.ok(responseDto);
    }

    @GetMapping("/{id}")
    @Operation(summary = "특정 할일 조회", description = "현재 등록되어 있는 할일중에 특정 할일을 조회합니다.")
    public ResponseEntity<ScheduleResponseDto> findById(@PathVariable Long id) {
        ScheduleResponseDto responseDto = scheduleService.findById(id);
        return ResponseEntity.ok(responseDto);
    }

    @PatchMapping("/{id}")
    @Operation(summary = "특정 할일 제목, 내용 수정", description = "등록되어 있는 특정 할일의 제목과 내용을 수정합니다.")
    public ResponseEntity<Void> updateTitleAndContents(
            @PathVariable Long id,
            @Valid @RequestBody UpdateScheduleTitleAndContentsRequestDto requestDto,
            HttpServletRequest request
    ) {
        UserResponseDto sessionUser = SessionUtil.getSessionUser(request);
        scheduleService.updateTitleAndContents(id, sessionUser.getId(), requestDto.getTitle(), requestDto.getContents());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "특정 할일 삭제", description = "등록되어 있는 특정 할일을 삭제합니다.")
    public ResponseEntity<Void> delete(
            @PathVariable Long id,
            HttpServletRequest request
    ) {
        UserResponseDto sessionUser = SessionUtil.getSessionUser(request);
        scheduleService.delete(id, sessionUser.getId());
        return ResponseEntity.ok().build();
    }

}
