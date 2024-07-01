package com.myblog.blog.controller;

import com.myblog.blog.dto.ArticleDTO;
import com.myblog.blog.model.Category;
import com.myblog.blog.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.myblog.blog.model.Article;
import com.myblog.blog.repository.ArticleRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/articles")
public class ArticleController {

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    // Convertit une entité Article en un ArticleDTO
    private ArticleDTO convertToDTO(Article article) {
        ArticleDTO articleDTO = new ArticleDTO();
        articleDTO.setId(article.getId());
        articleDTO.setTitle(article.getTitle());
        articleDTO.setContent(article.getContent());
        articleDTO.setCreatedAt(article.getCreatedAt());
        articleDTO.setUpdatedAt(article.getUpdatedAt());
        if (article.getCategory() != null) {
            articleDTO.setCategoryId(article.getCategory().getId());
        }
        return articleDTO;
    }

    // Convertit un ArticleDTO en une entité Article
    private Article convertToEntity(ArticleDTO articleDTO) {
        Article article = new Article();
        article.setId(articleDTO.getId());
        article.setTitle(articleDTO.getTitle());
        article.setContent(articleDTO.getContent());
        article.setCreatedAt(articleDTO.getCreatedAt());
        article.setUpdatedAt(articleDTO.getUpdatedAt());
        if (articleDTO.getCategoryId() != null) {
            Category category = categoryRepository.findById(articleDTO.getCategoryId()).orElse(null);
            article.setCategory(category);
        }
        return article;
    }

    // Méthodes CRUD de base

    // Méthode pour lister tous les articles
    @GetMapping
    public ResponseEntity<List<ArticleDTO>> getAllArticles() {
        List<Article> articles = articleRepository.findAll();
        if (articles.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        List<ArticleDTO> articleDTOs = articles.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(articleDTOs);
    }

    // Méthode pour récupérer un article par son ID
    @GetMapping("/{id}")
    public ResponseEntity<ArticleDTO> getArticleById(@PathVariable Long id) {
        Article article = articleRepository.findById(id).orElse(null);
        if (article == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(convertToDTO(article));
    }

    // Méthode pour créer un nouvel article
    @PostMapping
    public ResponseEntity<ArticleDTO> createArticle(@RequestBody ArticleDTO articleDTO) {
        Article article = convertToEntity(articleDTO);
        article.setCreatedAt(LocalDateTime.now());
        article.setUpdatedAt(LocalDateTime.now());

        // Ajout de la catégorie
        if (articleDTO.getCategoryId() != null) {
            Category category = categoryRepository.findById(articleDTO.getCategoryId()).orElse(null);
            if (category == null) {
                return ResponseEntity.badRequest().body(null); // Retourne une réponse 400 Bad Request si la catégorie n'est pas trouvée
            }
            article.setCategory(category);
        }

        Article savedArticle = articleRepository.save(article);
        ArticleDTO savedArticleDTO = convertToDTO(savedArticle);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedArticleDTO);
    }

    // Méthode pour mettre à jour un article existant
    @PutMapping("/{id}")
    public ResponseEntity<ArticleDTO> updateArticle(@PathVariable Long id, @RequestBody ArticleDTO articleDTO) {
        Article article = articleRepository.findById(id).orElse(null);
        if (article == null) {
            return ResponseEntity.notFound().build();
        }

        article.setTitle(articleDTO.getTitle());
        article.setContent(articleDTO.getContent());
        article.setUpdatedAt(LocalDateTime.now());

        // Mise à jour de la catégorie
        if (articleDTO.getCategoryId() != null) {
            Category category = categoryRepository.findById(articleDTO.getCategoryId()).orElse(null);
            if (category == null) {
                return ResponseEntity.badRequest().body(null); // Retourne une réponse 400 Bad Request si la catégorie n'est pas trouvée
            }
            article.setCategory(category);
        }

        Article updatedArticle = articleRepository.save(article);
        ArticleDTO updatedArticleDTO = convertToDTO(updatedArticle);
        return ResponseEntity.ok(updatedArticleDTO);
    }

    // Méthode pour supprimer un article
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

    //Méthode de recherche d'un article par titre
    @GetMapping("title/{title}")
    public ResponseEntity<List<Article>> getArticlesByTitle(@PathVariable String title) {
        List<Article> articles = articleRepository.findByTitle(title);
        if (articles.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(articles);
    }

    // Méthode de recherche d'un article par contenu
    @GetMapping("content/{content}")
    public ResponseEntity<List<Article>> getArticlesByContent(@PathVariable String content) {
        List<Article> articles = articleRepository.findByContentContaining(content);
        if (articles.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(articles);
    }

    // Méthode de recherche des articles créés après une certaine date
    @GetMapping("/created-after/{date}")
    public ResponseEntity<List<Article>> getArticlesCreatedAfter(@PathVariable LocalDateTime date) {
        List<Article> articles = articleRepository.findByCreatedAtAfter(date);
        if (articles.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(articles);
    }

    // Méthode pour récupérer les 5 derniers articles
    @GetMapping("/latest")
    public ResponseEntity<List<Article>> getLatestArticles() {
        List<Article> articles = articleRepository.findTop5ByOrderByCreatedAtDesc();
        if (articles.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(articles);
    }
}
