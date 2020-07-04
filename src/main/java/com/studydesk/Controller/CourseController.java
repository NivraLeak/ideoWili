package com.studydesk.Controller;

import com.studydesk.Model.Career;
import com.studydesk.Model.Course;
import com.studydesk.Resource.CareerResource;
import com.studydesk.Resource.CourseResource;
import com.studydesk.Resource.SaveCareerResource;
import com.studydesk.Resource.SaveCourseResource;
import com.studydesk.Service.CourseService;
import com.studydesk.repository.CourseRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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
@Tag(name = "courses", description = "Courses API")
@RestController
@RequestMapping("/api")
public class CourseController {
    @Autowired
    private ModelMapper mapper;

    @Autowired
    private CourseService courseService;

    @Operation(summary = "Get Courses", description = "Get All Courses by Pages", tags = { "courses" })
    @GetMapping("/courses")
    public Page<CourseResource> getAllCourses(
            @Parameter(description="Pageable Parameter")
                    Pageable pageable) {
        Page<Course> postsPage = courseService.getAllCourses(pageable);
        List<CourseResource> resources = postsPage.getContent().stream().map(this::convertToResource).collect(Collectors.toList());

        return new PageImpl<>(resources, pageable, resources.size());
    }

    @Operation(summary = "Get Courses by Id", description = "Get a Courses by specifying Id", tags = { "courses" })
    @GetMapping("/courses/{id}")
    public CourseResource getCareerById(
            @Parameter(description="Post Id")
            @PathVariable(name = "id") Long postId) {
        return convertToResource(courseService.getCourseById(postId));
    }

    @PostMapping("/courses")
    public CourseResource createCourse(@Valid @RequestBody SaveCourseResource resource){
        Course course = convertToEntity(resource);
        return convertToResource(courseService.createCourse(course));
    }

    @PutMapping("/courses/{id}")
    public CourseResource updateCourse(@PathVariable(name = "id") Long postId, @Valid @RequestBody SaveCourseResource resource) {
        Course course = convertToEntity(resource);
        return convertToResource(courseService.updateCourse(postId,course));
    }

    @DeleteMapping("/courses/{id}")
    public ResponseEntity<?> deleteCourse(@PathVariable(name = "id") Long postId) {
        return courseService.deleteCourses(postId);
    }

    @GetMapping("/tags/{tagId}/courses")
    public Page<CourseResource>  getAllCoursesByCareerId(@PathVariable(name = "tagId") Long tagId, Pageable pageable){
        Page<Course> postPage = courseService.getAllCoursesByCareerId(tagId, pageable);
        List<CourseResource> resources = postPage.getContent().stream().map(this::convertToResource).collect(Collectors.toList());
        return new PageImpl<>(resources, pageable, resources.size());
    }

    @PostMapping("/courses/{courseId}/careers/{careerId}")
    public CourseResource assingCoursesCareer(@PathVariable(name = "courseId") Long postId,
                                              @PathVariable(name = "careerId") Long tagId){
        return convertToResource(courseService.assignCourseCareer(postId, tagId));
    }

    @DeleteMapping("/courses/{courseId}/careers/{careerId}")
    public CourseResource unassignCourseCareer(@PathVariable(name = "courseId") Long postId,
                                               @PathVariable(name = "careerId") Long tagId) {
        return convertToResource(courseService.unassignCourseCareer(postId, tagId));
    }

    private Course convertToEntity(SaveCourseResource resource){
        return mapper.map(resource, Course.class);
    }

    private CourseResource convertToResource(Course entity){
        return mapper.map(entity, CourseResource.class);
    }
}
