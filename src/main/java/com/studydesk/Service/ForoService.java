package com.studydesk.Service;

import com.studydesk.Model.Foro;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface ForoService {
    Foro assignForoTag(Long postId, Long tagId);
    Foro unassignForoTag(Long postId, Long tagId);
    Page<Foro> getAllForosByTagId(Long tagId, Pageable pageable);
    ResponseEntity<?> deleteForo(Long foroId);
    Foro updateForo(Long foroId, Foro foroRequest);
    Foro createForo(Foro foro);
    Foro getForoById(Long foroId);
    Page<Foro> getAllForos(Pageable pageable);
}
