package com.myblog.blog.controller;

import com.myblog.blog.dto.TagDTO;
import com.myblog.blog.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tags")
public class TagController {

    @Autowired
    private TagService tagService;

    @GetMapping
    public ResponseEntity<List<TagDTO>> getAllTags() {
        List<TagDTO> tagDTOs = tagService.getAllTags();
        if (tagDTOs.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(tagDTOs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TagDTO> getTagById(@PathVariable Long id) {
        TagDTO tagDTO = tagService.getTagById(id);
        if (tagDTO == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(tagDTO);
    }

    @PostMapping
    public ResponseEntity<TagDTO> createTag(@RequestBody TagDTO tagDTO) {
        TagDTO createdTagDTO = tagService.createTag(tagDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTagDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TagDTO> updateTag(@PathVariable Long id, @RequestBody TagDTO tagDTO) {
        TagDTO updatedTagDTO = tagService.updateTag(id, tagDTO);
        if (updatedTagDTO == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedTagDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTag(@PathVariable Long id) {
        tagService.deleteTag(id);
        return ResponseEntity.noContent().build();
    }
}
