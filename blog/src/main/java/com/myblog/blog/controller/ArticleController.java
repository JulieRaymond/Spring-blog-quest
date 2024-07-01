package com.myblog.blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.myblog.blog.model.Article;
import com.myblog.blog.repository.ArticleRepository;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/articles")
public class ArticleController {

    @Autowired
    private ArticleRepository articleRepository;

    // Méthodes CRUD de base

    @GetMapping
    public ResponseEntity<List<Article>> getAllArticles() {
        List<Article> articles = articleRepository.findAll();
        if (articles.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(articles);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Article> getArticleById(@PathVariable Long id) {
        Article article = articleRepository.findById(id).orElse(null);
        if (article == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(article);
    }

    @PostMapping
    public ResponseEntity<Article> createArticle(@RequestBody Article article) {
        article.setCreatedAt(LocalDateTime.now());
        article.setUpdatedAt(LocalDateTime.now());
        Article savedArticle = articleRepository.save(article);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedArticle);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Article> updateArticle(@PathVariable Long id, @RequestBody Article articleDetails) {
        Article article = articleRepository.findById(id).orElse(null);
        if (article == null) {
            return ResponseEntity.notFound().build();
        } else {
            article.setTitle(articleDetails.getTitle());
            article.setContent(articleDetails.getContent());
            article.setUpdatedAt(LocalDateTime.now());
            Article updatedArticle = articleRepository.save(article);
            return ResponseEntity.ok(updatedArticle);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteArticle(@PathVariable Long id) {
        Article article = articleRepository.findById(id).orElse(null);
        if (article == null) {
            return ResponseEntity.notFound().build();
        }
        articleRepository.delete(article);
        return ResponseEntity.noContent().build();
    }

    //Méthodes de recherche personnalisées

    @GetMapping("title/{title}")
    public ResponseEntity<List<Article>> getArticlesByTitle(@PathVariable String title) {
        List<Article> articles = articleRepository.findByTitle(title);
        if (articles.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(articles);
    }

    @GetMapping("content/{content}")
    public ResponseEntity<List<Article>> getArticlesByContent(@PathVariable String content) {
        List<Article> articles = articleRepository.findByContentContaining(content);
        if (articles.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(articles);
    }

    @GetMapping("/created-after/{date}")
    public ResponseEntity<List<Article>> getArticlesCreatedAfter(@PathVariable LocalDateTime date) {
        List<Article> articles = articleRepository.findByCreatedAtAfter(date);
        if (articles.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(articles);
    }

    @GetMapping("/latest")
    public ResponseEntity<List<Article>> getLatestArticles() {
        List<Article> articles = articleRepository.findTop5ByOrderByCreatedAtDesc();
        if (articles.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(articles);
    }
}
