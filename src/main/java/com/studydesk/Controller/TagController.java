package com.studydesk.Controller;

import com.studydesk.Model.Tag;
import com.studydesk.Resource.SaveTagResource;
import com.studydesk.Resource.TagResource;
import com.studydesk.Service.TagService;
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
public class TagController {

    @Autowired
    private ModelMapper mapper;
    @Autowired
    private TagService tagService;

    @GetMapping("/tags")
    public Page<TagResource> getAllTags(Pageable pageable) {
        List<TagResource> tags = tagService.getAllTags(pageable)
                .getContent().stream().map(this::convertToResource).collect(Collectors.toList());
        int tagsCount = tags.size();
        return new PageImpl<>(tags, pageable, tagsCount);
    }

    @GetMapping("/foros/{foroId}/tags")
    public Page<TagResource> getAllTagsByPostId(@PathVariable(name = "foroId") Long foroId, Pageable pageable) {
        List<TagResource> tags = tagService.getAllTagsByForoId(foroId, pageable).getContent().stream().map(this::convertToResource).collect(Collectors.toList());
        int tagCount = tags.size();
        return new PageImpl<>(tags, pageable, tagCount);
    }

    @GetMapping("/tags/{id}")
    public TagResource getTagById(@PathVariable(name = "id") Long tagId) {
        return convertToResource(tagService.getTagById(tagId));
    }

    @PostMapping("/tags")
    public TagResource createTag(@Valid @RequestBody SaveTagResource resource) {
        return convertToResource(tagService.createTag(convertToEntity(resource)));
    }
    @PutMapping("/tags/{id}")
    public TagResource updateTag(@PathVariable(name = "id") Long tagId, @Valid @RequestBody SaveTagResource resource) {
        return convertToResource(tagService.updateTag(tagId, convertToEntity(resource)));
    }

    @DeleteMapping("/tags/{id}")
    public ResponseEntity<?> deleteTag(@PathVariable(name = "id") Long tagId) {
        return tagService.deleteTag(tagId);
    }

    private Tag convertToEntity(SaveTagResource resource) {
        return mapper.map(resource, Tag.class);
    }

    private TagResource convertToResource(Tag entity) {
        return mapper.map(entity, TagResource.class);
    }
}
