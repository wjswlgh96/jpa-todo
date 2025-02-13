package com.example.jpa_todo.domain.comment.controller;

import com.example.jpa_todo.common.utils.SessionUtil;
import com.example.jpa_todo.domain.comment.dto.request.CreateCommentRequestDto;
import com.example.jpa_todo.domain.comment.dto.request.UpdateCommentContentsRequestDto;
import com.example.jpa_todo.domain.comment.dto.request.CommentResponseDto;
import com.example.jpa_todo.domain.user.dto.response.UserResponseDto;
import com.example.jpa_todo.domain.comment.service.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comments")
@RequiredArgsConstructor
@Tag(name = "Comment API", description = "댓글 관련 API")
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    @Operation(summary = "댓글 등록", description = "현재 로그인되어 있는 유저의 ID로 할일에 댓글을 등록합니다.")
    public ResponseEntity<CommentResponseDto> createComment(
            @Valid @RequestBody CreateCommentRequestDto requestDto,
            HttpServletRequest request
    ) {
        UserResponseDto sessionUser = SessionUtil.getSessionUser(request);
        CommentResponseDto responseDto = commentService.createComment(sessionUser.getId(), requestDto.getScheduleId(), requestDto.getContents());
        return ResponseEntity.ok(responseDto);
    }

    @GetMapping
    @Operation(summary = "모든 댓글 조회", description = "현재 등록되어 있는 모든 댓글을 조회합니다.")
    public ResponseEntity<List<CommentResponseDto>> getComments() {
        List<CommentResponseDto> responseDto = commentService.getComments();
        return ResponseEntity.ok(responseDto);
    }

    @GetMapping("/{id}")
    @Operation(summary = "특정 댓글 조회", description = "현재 등록되어 있는 댓글중에 특정 댓글을 조회합니다.")
    public ResponseEntity<CommentResponseDto> getCommentById(@PathVariable Long id) {
        CommentResponseDto responseDto = commentService.getCommentById(id);
        return ResponseEntity.ok(responseDto);
    }

    @PatchMapping("/{id}")
    @Operation(summary = "특정 댓글 내용 수정", description = "특정 댓글의 내용을 수정합니다.")
    public ResponseEntity<Void> updateContents(
            @PathVariable Long id,
            @Valid @RequestBody UpdateCommentContentsRequestDto requestDto,
            HttpServletRequest request
    ) {
        UserResponseDto sessionUser = SessionUtil.getSessionUser(request);
        commentService.updateContents(sessionUser.getId(), id, requestDto.getContents());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "특정 댓글 삭제", description = "특정 댓글을 삭제합니다.")
    public ResponseEntity<Void> deleteComment(
            @PathVariable Long id,
            HttpServletRequest request
    ) {
        UserResponseDto sessionUser = SessionUtil.getSessionUser(request);
        commentService.deleteComment(sessionUser.getId(), id);
        return ResponseEntity.ok().build();
    }
}
