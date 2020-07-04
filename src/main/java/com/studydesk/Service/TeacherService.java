package com.studydesk.Service;

import com.studydesk.Model.Teacher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface TeacherService {
Page<Teacher> getAllTeachers(Pageable pageable);
Page<Teacher> getAllTeachersByCourse(Long CourseId, Pageable pageable);

Teacher getTeacherById(Long teacherId);
Teacher createTeacher(Teacher teacher);
Teacher updateTeacher(Long teacherId, Teacher teacherRequest);

ResponseEntity<?> deleteTeacher(Long teacherId);

Teacher assignTeacherCourse(Long teacherId, Long courseId);
Teacher unassignTeacherCourse(Long teacherId, Long courseId);
}