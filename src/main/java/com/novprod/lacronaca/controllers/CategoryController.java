package com.novprod.lacronaca.controllers;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.novprod.lacronaca.dtos.ArticleDto;
import com.novprod.lacronaca.dtos.CategoryDto;
import com.novprod.lacronaca.models.Category;
import com.novprod.lacronaca.services.ArticleService;
import com.novprod.lacronaca.services.CategoryService;

@Controller
@RequestMapping("/categories")
public class CategoryController {
    @Autowired
    private ArticleService articleService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private ModelMapper modelMapper;

    @GetMapping("/search/{id}")
    public String categorySearch(@PathVariable("id") Long id, Model viewModel) {
        CategoryDto category = categoryService.read(id);
        viewModel.addAttribute("title", "Tutti gli articoli trovati per la categoria: " + category.getName());

        List<ArticleDto> articles = articleService.searchByCategory(modelMapper.map(category, Category.class));
        viewModel.addAttribute("articles", articles);
        return "article/articles";
    }
}
