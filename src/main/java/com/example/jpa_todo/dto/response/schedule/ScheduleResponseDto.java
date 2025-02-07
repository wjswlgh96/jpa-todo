package com.example.jpa_todo.dto.response.schedule;

import com.example.jpa_todo.dto.response.comment.CommentResponseInScheduleDto;
import com.example.jpa_todo.entity.Schedule;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@RequiredArgsConstructor
public class ScheduleResponseDto {

    @Schema(example = "1")
    private final Long id;

    @Schema(example = "1")
    private final Long userId;

    @Schema(example = "제목")
    private final String title;

    @Schema(example = "내용입니다!")
    private final String contents;

    @Schema(example = "[]")
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
