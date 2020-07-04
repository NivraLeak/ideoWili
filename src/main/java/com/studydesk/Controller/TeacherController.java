package com.studydesk.Controller;

import com.studydesk.Model.Teacher;
import com.studydesk.Resource.SaveTeacherResource;
import com.studydesk.Resource.TeacherResource;
import com.studydesk.Service.TeacherService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Tag(name = "teachers", description = "Teachers API")
@RestController
@RequestMapping("/api")
public class TeacherController {
    @Autowired
    private ModelMapper mapper;

    @Autowired
    private TeacherService teacherService;

    @GetMapping("/teachers")
    public Page<TeacherResource> getAllTeachers(Pageable pageable){
        Page<Teacher> teacherssPage = teacherService.getAllTeachers(pageable);
        List<TeacherResource> resources = teacherssPage.getContent().stream().map(this::convertToResource).collect(Collectors.toList());

        return new PageImpl<>(resources, pageable, resources.size());
    }

    @GetMapping("/teachers/{id}")
    public TeacherResource getTeacherById(@PathVariable(name = "id") Long teacherId){
        return convertToResource(teacherService.getTeacherById(teacherId));
    }

    @GetMapping("/courses/{courseId}/teachers")
    public Page<TeacherResource> getAllTeachersByCourseId(@PathVariable(name = "courseId") Long courseId, Pageable pageable){
        Page<Teacher> teachersPage = teacherService.getAllTeachersByCourse(courseId, pageable);
        List<TeacherResource> resources = teachersPage.getContent().stream().map(this::convertToResource).collect(Collectors.toList());
        return new PageImpl<>(resources, pageable, resources.size());
    }

    @PostMapping("/teachers")
    public TeacherResource createUser(@Valid @RequestBody SaveTeacherResource resource)  {
        Teacher teacher = convertToEntity(resource);
        return convertToResource(teacherService.createTeacher(teacher));
    }

    @PutMapping("/teachers/{id}")
    public TeacherResource updateTeacher(@PathVariable(name = "id") Long teacherId, @Valid @RequestBody SaveTeacherResource resource) {
        Teacher teacher = convertToEntity(resource);
        return convertToResource(teacherService.updateTeacher(teacherId, teacher));
    }

    @DeleteMapping("/teachers/{id}")
    public ResponseEntity<?> deleteTeacher(@PathVariable(name = "id") Long teacherId) {
        return teacherService.deleteTeacher(teacherId);
    }

    @PostMapping("/teachers/{teacherId}/courses/{courseId}")
    public TeacherResource assignTeacherCourse(@PathVariable(name = "teacherId") Long teacherId,
                                          @PathVariable(name = "courseId") Long courseId) {
        return convertToResource(teacherService.assignTeacherCourse(teacherId, courseId));
    }

    @DeleteMapping("/teachers/{teacherId}/courses/{courseId}")
    public TeacherResource unAssignUserCompany(@PathVariable(name = "teacherId") Long teacherId,
                                            @PathVariable(name = "courseId") Long courseId) {

        return convertToResource(teacherService.unassignTeacherCourse(teacherId, courseId));
    }

    private Teacher convertToEntity(SaveTeacherResource resource) { return mapper.map(resource, Teacher.class); }

    private TeacherResource convertToResource(Teacher entity) { return mapper.map(entity, TeacherResource.class); }

}
