package com.studydesk.repository;

import com.studydesk.Model.Topíc;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TopicRepository extends JpaRepository<Topíc, Long> {
    Page<Topíc> findByCourseId(Long courseId, Pageable pageable);
    Optional<Topíc> findByIdAndCourseId(Long id, Long courseId);
}
