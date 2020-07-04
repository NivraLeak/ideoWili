package com.studydesk.Service;

import com.studydesk.Model.Career;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface CareerService {
    Career assignCareerCourse(Long careerId, Long courseId);
    Career unassignCareerCourse(Long careerId, Long courseId);
    Page<Career> getAllCareersByCourseId(Long courseId, Pageable pageable);
    ResponseEntity<?> deleteCareer(Long careerId);
    Career updateCareer(Long careerId, Career  careerRequest);
    Career createCareer(Career career);
    Career getCareerById(Long careerId);
    Page<Career> getAllCareers(Pageable pageable);

}
