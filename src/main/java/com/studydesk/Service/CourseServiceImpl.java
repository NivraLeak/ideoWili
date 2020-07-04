package com.studydesk.Service;

import com.studydesk.Exception.ResourceNotFoundException;
import com.studydesk.Model.Career;
import com.studydesk.Model.Course;
import com.studydesk.repository.CareerRepository;
import com.studydesk.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class CourseServiceImpl implements  CourseService{

    @Autowired
    private CareerRepository careerRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Override
    public Page<Course> getAllCourses(Pageable pageable) {
        return courseRepository.findAll(pageable);
    }

    @Override
    public Page<Course> getAllCoursesByCareerId(Long careerId, Pageable pageable) {
        return careerRepository.findById(careerId).map(career -> {
            List<Course> courses = career.getCourses();
            int coursesCount = courses.size();
            return new PageImpl<>(courses, pageable, coursesCount);
        })
                .orElseThrow(() -> new ResourceNotFoundException("Career", "Id", careerId));
    }

    @Override
    public Course getCourseById(Long courseId) {
        return courseRepository.findById(courseId)
                .orElseThrow(() -> new ResourceNotFoundException("Course"+ "Id" +courseId));
    }

    @Override
    public Course createCourse(Course courseId) {
        return courseRepository.save(courseId);
    }

    @Override
    public Course updateCourse(Long courseId, Course courseDetails) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new ResourceNotFoundException("Course", "Id", courseId));
        course.setTitle(courseDetails.getTitle());
        return courseRepository.save(course);
    }

    @Override
    public ResponseEntity<?> deleteCourses(Long courseId) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new ResourceNotFoundException("Course", "Id", courseId));
        courseRepository.delete(course);
        return ResponseEntity.ok().build();
    }

    @Override
    public Course assignCourseCareer(Long courseId, Long careerId) {
        Career career = careerRepository.findById(careerId)
                .orElseThrow(() -> new ResourceNotFoundException("Career", "Id", careerId));
        return courseRepository.findById(courseId).map(course -> {
            if (!course.getCareers().contains(career)) {
                course.getCareers().add(career);
                return courseRepository.save(course);
            }
            return course;
        }).orElseThrow(() -> new ResourceNotFoundException("Course", "Id", courseId));
    }

    @Override
    public Course unassignCourseCareer(Long courseId, Long careerId) {
        Career career = careerRepository.findById(careerId)
                .orElseThrow(() -> new ResourceNotFoundException("Career", "Id", careerId));
        return courseRepository.findById(courseId).map(course -> {
            course.getCareers().remove(career);
            return courseRepository.save(course);
        }).orElseThrow(() -> new ResourceNotFoundException("Course", "Id", courseId));
    }
}
