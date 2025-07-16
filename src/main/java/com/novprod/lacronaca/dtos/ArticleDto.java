package com.novprod.lacronaca.dtos;

import java.time.LocalDate;

import com.novprod.lacronaca.models.Category;
import com.novprod.lacronaca.models.Image;
import com.novprod.lacronaca.models.User;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ArticleDto {
    private long id;
    private String title;
    private String subtitle;
    private String body;
    private LocalDate publishDate;
    private Boolean isAccepted;
    private User user;
    private Category category;
    private Image image;
}
