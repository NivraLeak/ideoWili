package com.studydesk.Service;

import com.studydesk.Model.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface TagService {
    Page<Tag> getAllTags(Pageable pageable);
    Page<Tag> getAllTagsByForoId(Long foroId, Pageable pageable);
    Tag getTagById(Long tagId);
    Tag createTag(Tag tag);
    Tag updateTag(Long tagId, Tag tagDetails);
    ResponseEntity<?> deleteTag(Long tagId);


}
