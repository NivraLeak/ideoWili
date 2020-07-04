package com.studydesk.Service;

import com.studydesk.Exception.ResourceNotFoundException;
import com.studydesk.Model.Topíc;
import com.studydesk.repository.CourseRepository;
import com.studydesk.repository.TopicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TopicServiceImpl implements TopicService{
    @Autowired
    private TopicRepository topicRepository;

    @Autowired
    private CourseRepository courseRepository;


    @Override
    public Page<Topíc> getAllTopicsByCourseId(Long courseId, Pageable pageable) {
        return topicRepository.findByCourseId(courseId, pageable);
    }

    @Override
    public Topíc getTopicByIdAndCourseId(Long courseId, Long topicId) {
        return topicRepository.findByIdAndCourseId(topicId, courseId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Topic not found with Id" + topicId +
                                " ans CourseId " + courseId
                ));
    }

    @Override
    public Topíc createTopic(Long courseId, Topíc topicId) {
        return courseRepository.findById(courseId).map(course -> {
            topicId.setCourse(course);
            return topicRepository.save(topicId);
        }).orElseThrow(() -> new ResourceNotFoundException("Course", "Id", courseId));
    }

    @Override
    public Topíc updateTopic(Long courseId, Long topicId, Topíc topicDetails) {
        if(!courseRepository.existsById(courseId))
            throw  new ResourceNotFoundException("Course", "Id", courseId);

        return topicRepository.findById(topicId).map(topic -> {
            topic.setName(topicDetails.getName());
            topic.setDescription(topicDetails.getDescription());
            return topicRepository.save(topic);
        }).orElseThrow(() -> new ResourceNotFoundException("Topic", "Id", topicId));
    }

    @Override
    public ResponseEntity<?> deleteTopic(Long courseId, Long topicId) {
        return topicRepository.findByIdAndCourseId(topicId, courseId).map(topic -> {
            topicRepository.delete(topic);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ResourceNotFoundException(
                " Topic not found with Id " + topicId + " and CourseId " + courseId
        ));
    }

    @Override
    public List<Topíc> getAllTopics() {
        return topicRepository.findAll();
    }
}
