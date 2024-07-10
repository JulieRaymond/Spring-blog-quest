package com.myblog.blog.service;

import com.myblog.blog.dto.TagDTO;
import com.myblog.blog.mapper.TagMapper;
import com.myblog.blog.model.Tag;
import com.myblog.blog.model.Article;
import com.myblog.blog.repository.TagRepository;
import com.myblog.blog.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TagService {

    @Autowired
    private TagRepository tagRepository;

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private TagMapper tagMapper;

    public List<TagDTO> getAllTags() {
        List<Tag> tags = tagRepository.findAll();
        return tags.stream()
                .map(tagMapper::convertToDTO)
                .collect(Collectors.toList());
    }

    public TagDTO getTagById(Long id) {
        Optional<Tag> optionalTag = tagRepository.findById(id);
        return optionalTag.map(tagMapper::convertToDTO).orElse(null);
    }

    public TagDTO createTag(TagDTO tagDTO) {
        List<Article> articles = articleRepository.findAllById(tagDTO.getArticleIds());
        Tag tag = tagMapper.convertToEntity(tagDTO, articles);
        Tag savedTag = tagRepository.save(tag);
        return tagMapper.convertToDTO(savedTag);
    }

    public TagDTO updateTag(Long id, TagDTO tagDTO) {
        Optional<Tag> optionalTag = tagRepository.findById(id);
        if (optionalTag.isEmpty()) {
            return null;
        }

        List<Article> articles = articleRepository.findAllById(tagDTO.getArticleIds());
        Tag tag = optionalTag.get();
        tag.setName(tagDTO.getName());
        tag.setArticles(articles);
        Tag updatedTag = tagRepository.save(tag);
        return tagMapper.convertToDTO(updatedTag);
    }

    public void deleteTag(Long id) {
        Optional<Tag> optionalTag = tagRepository.findById(id);
        optionalTag.ifPresent(tagRepository::delete);
    }
}
