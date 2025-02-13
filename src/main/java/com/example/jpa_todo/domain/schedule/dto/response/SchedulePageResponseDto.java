package com.example.jpa_todo.domain.schedule.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class SchedulePageResponseDto<T> {

    private final List<T> content;

    @Schema(example = "10")
    private final int size;

    @Schema(example = "1")
    private final int number;

    @Schema(example = "1")
    private final long totalElements;

    @Schema(example = "1")
    private final int totalPages;

    public static <T> SchedulePageResponseDto<T> toDto(Page<T> page) {
        return new SchedulePageResponseDto<>(
                page.getContent(),
                page.getSize(),
                page.getNumber() + 1,
                page.getTotalElements(),
                page.getTotalPages()
        );
    }
}
