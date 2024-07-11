package com.myblog.blog.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class ArticleDTO {

    @NotNull(message = "ID ne doit pas être nul")
    @Positive(message = "ID doit être un nombre positif")
    private Long id;

    @NotBlank(message = "Le titre ne doit pas être vide")
    @Size(min = 2, max = 50, message = "Le titre doit contenir entre 2 et 50 caractères")
    private String title;

    @NotBlank(message = "Le contenu ne doit pas être vide")
    @Size(min = 10, message = "Le contenu doit contenir au moins 10 caractères")
    private String content;

    @NotNull(message = "La date de création ne doit pas être nulle")
    @PastOrPresent(message = "La date de création ne peut pas être dans le futur")
    private LocalDateTime createdAt;

    @PastOrPresent(message = "La date de mise à jour ne peut pas être dans le futur")
    private LocalDateTime updatedAt;

    @NotNull(message = "L'ID de la catégorie ne doit pas être nul")
    @Positive(message = "L'ID de la catégorie doit être un nombre positif")
    private Long categoryId;

    private List<Long> tagIds;

    private List<ArticleAuthorDTO> authors;
}