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
public class CareerServiceImpl  implements  CareerService{
    @Autowired
    private CareerRepository careerRepository;

    @Autowired
    private CourseRepository courseRepository;


    @Override
    public Career assignCareerCourse(Long careerId, Long courseId) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new ResourceNotFoundException("Course", "Id", courseId));
        return careerRepository.findById(careerId).map(career -> {
            if(!career.getCourses().contains(course)) {
                career.getCourses().add(course);
                return careerRepository.save(career);
            }
            return career;
        }).orElseThrow(() -> new ResourceNotFoundException("Career", "Id", careerId));

    }

    @Override
    public Career unassignCareerCourse(Long careerId, Long courseId) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new ResourceNotFoundException("Course", "Id", courseId));
        return careerRepository.findById(careerId).map(career -> {
            career.getCourses().remove(course);
            return careerRepository.save(career);
        }).orElseThrow(() -> new ResourceNotFoundException("Career", "Id", careerId));

    }

    @Override
    public Page<Career> getAllCareersByCourseId(Long courseId, Pageable pageable) {
        return courseRepository.findById(courseId).map(course -> {
            List<Career> careers = course.getCareers();
            int careersCount = careers.size();
            return new PageImpl<>(careers, pageable, careersCount);
        })
                .orElseThrow(() -> new ResourceNotFoundException("Course", "Id", courseId));
    }

    @Override
    public ResponseEntity<?> deleteCareer(Long careerId) {
        Career post = careerRepository.findById(careerId)
                .orElseThrow(() -> new ResourceNotFoundException("Career", "Id", careerId));
        careerRepository.delete(post);
        return ResponseEntity.ok().build();
    }

    @Override
    public Career updateCareer(Long careerId, Career careerRequest) {
        Career career = careerRepository.findById(careerId)
                .orElseThrow(() -> new ResourceNotFoundException("Career", "Id", careerId));
        career.setTitle(careerRequest.getTitle());
        return careerRepository.save(career);
    }

    @Override
    public Career createCareer(Career career) {
        return careerRepository.save(career);
    }

    @Override
    public Career getCareerById(Long careerId) {
        return careerRepository.findById(careerId)
                .orElseThrow(() -> new ResourceNotFoundException("Career", "Id", careerId));
    }

    @Override
    public Page<Career> getAllCareers(Pageable pageable) {

        return careerRepository.findAll(pageable);
    }
}
