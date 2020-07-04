package com.studydesk.Service;

import com.studydesk.Exception.ResourceNotFoundException;
import com.studydesk.Model.Foro;
import com.studydesk.Model.Tag;
import com.studydesk.repository.ForoRepository;
import com.studydesk.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ForoServiceImpl implements ForoService {

    @Autowired
    private TagRepository tagRepository;
    @Autowired
    private ForoRepository foroRepository;

    @Override
    public Foro assignForoTag(Long foroId, Long tagId) {
        Tag tag = tagRepository.findById(tagId)
                .orElseThrow(() -> new ResourceNotFoundException("Tag", "Id", tagId));
        return foroRepository.findById(foroId).map(post -> {
            if(!post.getTags().contains(tag)) {
                post.getTags().add(tag);
                return foroRepository.save(post);
            }
            return post;
        }).orElseThrow(() -> new ResourceNotFoundException("Foro", "Id", foroId));

    }

    @Override
    public Foro unassignForoTag(Long foroId, Long tagId) {
        Tag tag = tagRepository.findById(tagId)
                .orElseThrow(() -> new ResourceNotFoundException("Tag", "Id", tagId));
        return foroRepository.findById(foroId).map(post -> {
            post.getTags().remove(tag);
            return foroRepository.save(post);
        }).orElseThrow(() -> new ResourceNotFoundException("Foro", "Id", foroId));
    }

    public Page<Foro> getAllForosByTagId(Long tagId, Pageable pageable) {
        return tagRepository.findById(tagId).map(tag -> {
            List<Foro> foros = tag.getForos();
            int forosCount = foros.size();
            return new PageImpl<>(foros, pageable, forosCount);
        })
                .orElseThrow(() -> new ResourceNotFoundException("Tag", "Id", tagId));
    }

    @Override
    public ResponseEntity<?> deleteForo(Long foroId) {
        Foro post = foroRepository.findById(foroId)
                .orElseThrow(() -> new ResourceNotFoundException("Foro", "Id", foroId));
        foroRepository.delete(post);
        return ResponseEntity.ok().build();
    }

    @Override
    public Foro updateForo(Long foroId, Foro foroRequest) {
        Foro foro = foroRepository.findById(foroId)
                .orElseThrow(() -> new ResourceNotFoundException("Foro", "Id", foroId));
        foro.setTitle(foroRequest.getTitle());
        foro.setDescription(foroRequest.getDescription());
        foro.setContent(foroRequest.getContent());
        return foroRepository.save(foro);
    }

    @Override
    public Foro createForo(Foro foro) {
        return foroRepository.save(foro);
    }

    @Override
    public Foro getForoById(Long foroId) {
        return foroRepository.findById(foroId)
                .orElseThrow(() -> new ResourceNotFoundException("Foro", "Id", foroId));
    }

    @Override
    public Page<Foro> getAllForos(Pageable pageable) {
        return foroRepository.findAll(pageable);
    }

}
