package com.studydesk.Service;

import com.studydesk.Model.Career;
import com.studydesk.Model.Course;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface CourseService {
    Page<Course> getAllCourses(Pageable pageable);
    Page<Course> getAllCoursesByCareerId(Long careerId, Pageable pageable);
    Course getCourseById(Long courseId);
    Course createCourse(Course courseId);
    Course updateCourse(Long courseId, Course courseDetails);
    ResponseEntity<?> deleteCourses(Long courseId);
    Course assignCourseCareer(Long courseId, Long careerId);
    Course unassignCourseCareer(Long courseId, Long careerId);
}
