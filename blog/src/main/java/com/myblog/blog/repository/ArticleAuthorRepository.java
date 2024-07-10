package com.myblog.blog.repository;

import com.myblog.blog.model.ArticleAuthor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleAuthorRepository extends JpaRepository<ArticleAuthor, Long>{
}
