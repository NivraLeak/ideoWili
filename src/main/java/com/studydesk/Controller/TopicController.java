package com.studydesk.Controller;

import com.studydesk.Model.Topíc;
import com.studydesk.Resource.SaveTopicResource;
import com.studydesk.Resource.TopicResource;
import com.studydesk.Service.TopicService;
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

@RestController
@RequestMapping("/api")
public class TopicController {
    @Autowired
    private ModelMapper mapper;

    @Autowired
    private TopicService topicService;

    @GetMapping("/topics")
    public List<Topíc> getAllTopicss() {
        return topicService.getAllTopics();
    }

    @GetMapping("/courses/{courseId}/topics")
    public Page<TopicResource> getAllDocumentsByCourseId(
            @PathVariable(name = "courseId") Long courseId,
            Pageable pageable)
    {
        Page<Topíc> topicPage = topicService.getAllTopicsByCourseId(courseId, pageable);
        List<TopicResource> resources = topicPage.getContent().stream().map(this::convertToResource).collect(Collectors.toList());
        return new PageImpl<>(resources, pageable, resources.size());
    }

    @GetMapping("/courses/{courseId}/topics/{topicId}")
    public TopicResource getTopicByIdAndCourseId(@PathVariable(name = "courseId") Long courseId,
                                                     @PathVariable(name = "topicId") Long topicId){
        return convertToResource(topicService.getTopicByIdAndCourseId(courseId, topicId));
    }

    @PostMapping("/courses/{courseId}/topics")
    public TopicResource createTopic(@PathVariable(name = "courseId") Long courseId,
                                           @Valid @RequestBody SaveTopicResource resource){
        return convertToResource(topicService.createTopic(courseId, convertToEntity(resource)));
    }

    @PutMapping("/courses/{courseId}/topics/{topicId}")
    public TopicResource updateDocument(@PathVariable(name = "courseId") Long courseId,
                                           @PathVariable(name = "topicId") Long topicId,
                                           @Valid @RequestBody SaveTopicResource resource){
        return convertToResource(topicService.updateTopic(courseId, topicId, convertToEntity(resource)));
    }

    @DeleteMapping("/courses/{courseId}/topics/{topicId}")
    public ResponseEntity<?> deleteDocument(@PathVariable(name = "courseId") Long courseId,
                                            @PathVariable(name="topicId") Long topicId){
        return  topicService.deleteTopic(courseId, topicId);
    }

    private Topíc convertToEntity(SaveTopicResource resource) {
        return mapper.map(resource, Topíc.class);
    }

    private TopicResource convertToResource(Topíc entity) {
        return mapper.map(entity, TopicResource.class);
    }


}
