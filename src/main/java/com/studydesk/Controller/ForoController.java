package com.studydesk.Controller;

import com.studydesk.Model.Foro;
import com.studydesk.Resource.ForoResource;
import com.studydesk.Resource.SaveForoResource;
import com.studydesk.Service.ForoService;
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

@Tag(name = "foros", description = "Foros API")
@RestController
@RequestMapping("/api")
public class ForoController {
    @Autowired
    private ModelMapper mapper;

    @Autowired
    private ForoService foroService;

    @Operation(summary = "Get Foros", description = "Get All Foros by Pages", tags = { "foros" })
    @GetMapping("/foros")
    public Page<ForoResource> getAllForos(
            @Parameter(description="Pageable Parameter")
                    Pageable pageable) {
        Page<Foro> postsPage = foroService.getAllForos(pageable);
        List<ForoResource> resources = postsPage.getContent().stream().map(this::convertToResource).collect(Collectors.toList());

        return new PageImpl<>(resources, pageable, resources.size());
    }

    @Operation(summary = "Get Post by Id", description = "Get a Posts by specifying Id", tags = { "posts" })
    @GetMapping("/posts/{id}")
    public ForoResource getForoById(
            @Parameter(description="Post Id")
            @PathVariable(name = "id") Long postId) {
        return convertToResource(foroService.getForoById(postId));
    }

    @PostMapping("/posts")
    public ForoResource createPost(@Valid @RequestBody SaveForoResource resource)  {
        Foro post = convertToEntity(resource);
        return convertToResource(foroService.createForo(post));
    }

    @PutMapping("/posts/{id}")
    public ForoResource updatePost(@PathVariable(name = "id") Long postId, @Valid @RequestBody SaveForoResource resource) {
        Foro post = convertToEntity(resource);
        return convertToResource(foroService.updateForo(postId, post));
    }

    @DeleteMapping("/posts/{id}")
    public ResponseEntity<?> deletePost(@PathVariable(name = "id") Long postId) {
        return foroService.deleteForo(postId);
    }

    @GetMapping("/tags/{tagId}/posts")
    public Page<ForoResource> getAllPostsByTagId(@PathVariable(name = "tagId") Long tagId, Pageable pageable) {
        Page<Foro> postsPage = foroService.getAllForosByTagId(tagId, pageable);
        List<ForoResource> resources = postsPage.getContent().stream().map(this::convertToResource).collect(Collectors.toList());
        return new PageImpl<>(resources, pageable, resources.size());
    }

    @PostMapping("/posts/{postId}/tags/{tagId}")
    public ForoResource assignPostTag(@PathVariable(name = "postId") Long postId,
                                      @PathVariable(name = "tagId") Long tagId) {
        return convertToResource(foroService.assignForoTag(postId, tagId));
    }

    @DeleteMapping("/posts/{foroId}/tags/{tagId}")
    public ForoResource unassignForoTag(@PathVariable(name = "foroId") Long foroId,
                                        @PathVariable(name = "tagId") Long tagId) {

        return convertToResource(foroService.unassignForoTag(foroId, tagId));
    }

    private Foro convertToEntity(SaveForoResource resource) {
        return mapper.map(resource, Foro.class);
    }

    private ForoResource convertToResource(Foro entity) {
        return mapper.map(entity, ForoResource.class);
    }



}
