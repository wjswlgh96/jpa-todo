package com.example.jpa_todo.domain.comment.service;

import com.example.jpa_todo.domain.schedule.service.ScheduleService;
import com.example.jpa_todo.domain.user.service.UserService;
import com.example.jpa_todo.domain.comment.dto.request.CommentResponseDto;
import com.example.jpa_todo.domain.comment.entity.Comment;
import com.example.jpa_todo.domain.schedule.entity.Schedule;
import com.example.jpa_todo.domain.user.entity.User;
import com.example.jpa_todo.common.exception.ApplicationException;
import com.example.jpa_todo.domain.comment.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;

    private final UserService userService;
    private final ScheduleService scheduleService;

    @Transactional
    public CommentResponseDto createComment(Long sessionId, Long scheduleId, String contents) {
        User findUser = userService.findByIdOrElseThrow(sessionId);
        Schedule findSchedule = scheduleService.findByIdOrElseThrow(scheduleId);

        Comment comment = new Comment(contents);
        findUser.addComment(comment);
        findSchedule.addComment(comment);
        commentRepository.save(comment);

        return CommentResponseDto.toDto(comment);
    }

    public List<CommentResponseDto> getComments() {
        return commentRepository.findAll()
                .stream()
                .map(CommentResponseDto::toDto)
                .toList();
    }

    public CommentResponseDto getCommentById(Long id) {
        Comment findComment = findByIdOrElseThrow(id);
        return CommentResponseDto.toDto(findComment);
    }

    @Transactional
    public void updateContents(Long sessionId, Long id, String contents) {
        Comment findComment = findByIdOrElseThrow(id);

        if (!findComment.getUser().getId().equals(sessionId)) {
            throw new ApplicationException(HttpStatus.FORBIDDEN, "자신이 작성한 댓글만 수정이 가능합니다.");
        }

        findComment.updateContents(contents);
    }

    @Transactional
    public void deleteComment(Long sessionId, Long id) {
        Comment findComment = findByIdOrElseThrow(id);

        if (!findComment.getUser().getId().equals(sessionId)) {
            throw new ApplicationException(HttpStatus.FORBIDDEN, "자신이 작성한 댓글만 삭제가 가능합니다");
        }

        commentRepository.delete(findComment);
    }

    public Comment findByIdOrElseThrow(Long id) {
        return commentRepository.findById(id)
                .orElseThrow(() -> new ApplicationException(HttpStatus.NOT_FOUND, "해당 아이디의 댓글이 없습니다 id = " + id));
    }
}
