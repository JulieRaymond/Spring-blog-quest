package com.myblog.blog.repository;

import com.myblog.blog.model.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {

    List<Article> findByTitle(String title);
    List<Article> findByContentContaining(String content);
    List<Article> findByCreatedAtAfter(LocalDateTime date);
    List<Article> findTop5ByOrderByCreatedAtDesc();
}
