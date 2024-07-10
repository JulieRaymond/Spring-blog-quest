package com.myblog.blog.dto;

import lombok.Data;

import java.util.List;

@Data
public class AuthorDTO {
    private Long id;
    private String firstname;
    private String lastname;
    private List<ArticleAuthorDTO> articleAuthors;
}
