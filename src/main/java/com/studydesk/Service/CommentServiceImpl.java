package com.studydesk.Service;

import com.studydesk.Exception.ResourceNotFoundException;
import com.studydesk.Model.Comment;
import com.studydesk.Model.Foro;
import com.studydesk.repository.CommentRepository;
import com.studydesk.repository.ForoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private ForoRepository foroRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Override
    public Page<Comment> getAllCommentsByForoId(Long foroId, Pageable pageable) {
        return commentRepository.findByForoId(foroId, pageable);
    }

    @Override
    public Comment getCommentByIdAndForoId(Long foroId, Long commentId) {
        return commentRepository.findByIdAndForoId(commentId, foroId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Comment not found with Id " + commentId +
                                " and PostId " + foroId));
    }

    @Override
    public Comment createComment(Long foroId, Comment comment) {
        return foroRepository.findById(foroId).map(foro -> {
            comment.setForo(foro);
            return commentRepository.save(comment);
        }).orElseThrow(() -> new ResourceNotFoundException(
                "Post", "Id", foroId));

    }

    @Override
    public Comment updateComment(Long foroId, Long commentId, Comment commentDetails) {
        if(!foroRepository.existsById(foroId))
            throw new ResourceNotFoundException("Post", "Id", foroId);

        return commentRepository.findById(commentId).map(comment -> {
            comment.setText(commentDetails.getText());
            return commentRepository.save(comment);
        }).orElseThrow(() -> new ResourceNotFoundException("Comment", "Id", commentId));

    }

    @Override
    public ResponseEntity<?> deleteComment(Long foroId, Long commentId) {
        return commentRepository.findByIdAndForoId(commentId, foroId).map(comment -> {
            commentRepository.delete(comment);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ResourceNotFoundException(
                "Comment not found with Id " + commentId + " and PostId " + foroId));

    }

}
