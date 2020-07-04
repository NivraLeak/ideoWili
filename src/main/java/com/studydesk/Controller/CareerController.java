package com.studydesk.Controller;

import com.studydesk.Model.Career;
import com.studydesk.Resource.CareerResource;
import com.studydesk.Resource.SaveCareerResource;
import com.studydesk.Service.CareerService;
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

@Tag(name = "careers", description = "Careers API")
@RestController
@RequestMapping("/api")
public class CareerController {
    @Autowired
    private ModelMapper mapper;

    @Autowired
    private CareerService careerService;

    @Operation(summary = "Get Careers", description = "Get All Careers by Pages", tags = { "careers" })
    @GetMapping("/careers")
    public Page<CareerResource> getAllCareers(
            @Parameter(description="Pageable Parameter")
                    Pageable pageable) {
        Page<Career> postsPage = careerService.getAllCareers(pageable);
        List<CareerResource> resources = postsPage.getContent().stream().map(this::convertToResource).collect(Collectors.toList());

        return new PageImpl<>(resources, pageable, resources.size());
    }

    @Operation(summary = "Get Career by Id", description = "Get a Careers by specifying Id", tags = { "careers" })
    @GetMapping("/careers/{id}")
    public CareerResource getCareerById(
            @Parameter(description="Post Id")
            @PathVariable(name = "id") Long postId) {
        return convertToResource(careerService.getCareerById(postId));
    }

    @PostMapping("/careers")
    public CareerResource createCareer(@Valid @RequestBody SaveCareerResource resource)  {
        Career career = convertToEntity(resource);
        return convertToResource(careerService.createCareer(career));
    }

    @PutMapping("/careers/{id}")
    public CareerResource updateCareer(@PathVariable(name = "id") Long postId, @Valid @RequestBody SaveCareerResource resource) {
        Career career = convertToEntity(resource);
        return convertToResource(careerService.updateCareer(postId, career));
    }

    @DeleteMapping("/careers/{id}")
    public ResponseEntity<?> deleteCareer(@PathVariable(name = "id") Long postId) {
        return careerService.deleteCareer(postId);
    }

    @GetMapping("/tags/{tagId}/careers")
    public Page<CareerResource> getAllCareersByCourseId(@PathVariable(name = "tagId") Long tagId, Pageable pageable) {
        Page<Career> postsPage = careerService.getAllCareersByCourseId(tagId, pageable);
        List<CareerResource> resources = postsPage.getContent().stream().map(this::convertToResource).collect(Collectors.toList());
        return new PageImpl<>(resources, pageable, resources.size());
    }

    @PostMapping("/careers/{careerId}/courses/{courseId}")
    public CareerResource assignCareersCourse(@PathVariable(name = "careerId") Long postId,
                                      @PathVariable(name = "courseId") Long tagId) {
        return convertToResource(careerService.assignCareerCourse(postId, tagId)); }

    @DeleteMapping("/careers/{careerId}/courses/{courseId}")
    public CareerResource unassignCareersCourse(@PathVariable(name = "careerId") Long postId,
                                        @PathVariable(name = "courseId") Long tagId) {

        return convertToResource(careerService.unassignCareerCourse(postId, tagId));
    }

    private Career convertToEntity(SaveCareerResource resource) {
        return mapper.map(resource, Career.class);
    }

    private CareerResource convertToResource(Career entity) {
        return mapper.map(entity, CareerResource.class);
    }


}
