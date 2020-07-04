package com.studydesk.Service;

import com.studydesk.Exception.ResourceNotFoundException;
import com.studydesk.Model.Course;
import com.studydesk.Model.Teacher;
import com.studydesk.repository.CourseRepository;
import com.studydesk.repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeacherServiceImpl implements TeacherService{
    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Override
    public Page<Teacher> getAllTeachers(Pageable pageable) {
        return teacherRepository.findAll(pageable);
    }

    @Override
    public Page<Teacher> getAllTeachersByCourse(Long CourseId, Pageable pageable) {
        return  courseRepository.findById(CourseId).map(course -> {
            List<Teacher> teachers = course.getTeachers();
            int teacherCount = teachers.size();
            return new PageImpl<>(teachers, pageable, teacherCount);
        })
                .orElseThrow(() -> new ResourceNotFoundException("Course", "Id", CourseId));
    }

    @Override
    public Teacher getTeacherById(Long teacherId) {
        return teacherRepository.findById(teacherId)
                .orElseThrow(() -> new ResourceNotFoundException("Teacher", "Id", teacherId));
    }

    @Override
    public Teacher createTeacher(Teacher teacher) {
        return teacherRepository.save(teacher);
    }

    @Override
    public Teacher updateTeacher(Long teacherId, Teacher teacherRequest) {
        Teacher teacher = teacherRepository.findById(teacherId)
                .orElseThrow(() -> new ResourceNotFoundException("Teacher", "Id", teacherId));
        teacher.setName(teacherRequest.getName());
        teacher.setField(teacherRequest.getField());
        return teacherRepository.save(teacher);
    }

    @Override
    public ResponseEntity<?> deleteTeacher(Long teacherId) {
        Teacher teacher = teacherRepository.findById(teacherId)
                .orElseThrow(() -> new ResourceNotFoundException("Teacher", "Id", teacherId));
        teacherRepository.delete(teacher);
        return ResponseEntity.ok().build();
    }

    @Override
    public Teacher assignTeacherCourse(Long teacherId, Long courseId) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new ResourceNotFoundException("Course", "Id", courseId));
        return teacherRepository.findById(teacherId).map(teacher -> {
            if(!teacher.getCourses().contains(course)) {
                teacher.getCourses().add(course);
                return teacherRepository.save(teacher);
            }
            return teacher;
        }).orElseThrow(() -> new ResourceNotFoundException("Teacher", "Id", teacherId));
    }

    @Override
    public Teacher unassignTeacherCourse(Long teacherId, Long courseId) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new ResourceNotFoundException("Course", "Id", courseId));
        return teacherRepository.findById(teacherId).map(teacher -> {
            teacher.getCourses().remove(course);
            return teacherRepository.save(teacher);
        }).orElseThrow(() -> new ResourceNotFoundException("Teacher", "Id", teacherId));
    }
}
