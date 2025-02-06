package com.example.jpa_todo.dto.response.schedule;

import com.example.jpa_todo.dto.response.comment.CommentResponseInScheduleDto;
import com.example.jpa_todo.entity.Schedule;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@RequiredArgsConstructor
public class ScheduleResponseDto {

    private final Long id;

    private final Long userId;

    private final String title;

    private final String contents;

    private final List<CommentResponseInScheduleDto> comments;

    private final LocalDateTime createdAt;

    private final LocalDateTime modifiedAt;

    public static ScheduleResponseDto toDto(Schedule schedule) {
        List<CommentResponseInScheduleDto> comments = schedule.getComments()
                .stream()
                .map(CommentResponseInScheduleDto::toDto)
                .toList();

        return new ScheduleResponseDto(
                schedule.getId(),
                schedule.getUser().getId(),
                schedule.getTitle(),
                schedule.getContents(),
                comments,
                schedule.getCreatedAt(),
                schedule.getModifiedAt()
        );
    }
}
