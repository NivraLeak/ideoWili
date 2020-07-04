package com.studydesk.Service;

import com.studydesk.Model.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface CommentService {
    Page<Comment> getAllCommentsByForoId(Long foroId, Pageable pageable);
    Comment getCommentByIdAndForoId(Long foroId, Long commentId);
    Comment createComment(Long foroId, Comment comment);
    Comment updateComment(Long postId, Long commentId,Comment commentDetails);
    ResponseEntity<?> deleteComment(Long postId,Long commentId);
}
