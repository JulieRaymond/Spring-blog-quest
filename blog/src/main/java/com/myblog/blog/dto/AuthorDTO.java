package com.myblog.blog.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.util.List;

@Data
public class AuthorDTO {

    @NotNull(message = "ID ne doit pas être nul")
    @Positive(message = "ID doit être un nombre positif")
    private Long id;

    @NotBlank(message = "Le prénom ne doit pas être vide")
    @Size(min = 2, max = 50, message = "Le prénom doit contenir entre 2 et 50 caractères")
    private String firstname;

    @NotBlank(message = "Le nom de famille ne doit pas être vide")
    @Size(min = 2, max = 50, message = "Le nom de famille doit contenir entre 2 et 50 caractères")
    private String lastname;

    @NotEmpty(message = "Les articles ne doivent pas être vides")
    private List<ArticleAuthorDTO> articleAuthors;
}
