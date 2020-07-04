package com.studydesk.Service;

import com.studydesk.Model.Topíc;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface TopicService {
    Page<Topíc> getAllTopicsByCourseId(Long courseId, Pageable pageable);
    Topíc getTopicByIdAndCourseId(Long courseId, Long topicId);
    Topíc createTopic(Long courseId,Topíc topicId);
    Topíc updateTopic(Long courseId,Long topicId, Topíc topicDetails);
    ResponseEntity<?> deleteTopic(Long courseId, Long topicId);

    List<Topíc> getAllTopics();
}
