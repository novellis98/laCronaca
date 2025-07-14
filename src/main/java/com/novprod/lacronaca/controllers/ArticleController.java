package com.novprod.lacronaca.controllers;

import java.security.Principal;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.novprod.lacronaca.dtos.ArticleDto;
import com.novprod.lacronaca.dtos.CategoryDto;
import com.novprod.lacronaca.models.Article;
import com.novprod.lacronaca.models.Category;
import com.novprod.lacronaca.services.ArticleService;
import com.novprod.lacronaca.services.CrudService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/articles")
public class ArticleController {
    @Autowired
    private ArticleService articleService;
    @Autowired
    @Qualifier("categoryService")
    private CrudService<CategoryDto, Category, Long> categoryService;

    @GetMapping
    public String articleIndex(Model viewModel) {
        viewModel.addAttribute("title", "Tutti gli articoli");
        List<ArticleDto> articles = articleService.readAll();
        Collections.sort(articles,
                Comparator.comparing(ArticleDto::getPublishDate).reversed());
        viewModel.addAttribute("articles", articles);
        return "article/articles";
    }

    @GetMapping("create")
    public String articleCreate(Model viewModel) {
        viewModel.addAttribute("title", "Crea un nuovo articolo");
        viewModel.addAttribute("article", new Article());
        viewModel.addAttribute("categories", categoryService.readAll());
        return "article/create";
    }

    @PostMapping
    public String articleStore(@Valid @ModelAttribute("article") Article article, BindingResult result,
            RedirectAttributes redirectAttributes, Principal principal, MultipartFile file, Model viewModel) {
        if (result.hasErrors()) {
            viewModel.addAttribute("title", "Crea un articolo");
            viewModel.addAttribute("article", article);
            viewModel.addAttribute("categories", categoryService.readAll());
            return "article/create";
        }
        articleService.create(article, principal, file);
        redirectAttributes.addFlashAttribute("successMessage", "Articolo aggiunto con successo!");
        return "redirect:/";
    }

    @GetMapping("/detail/{id}")
    public String detailArticle(@PathVariable("id") Long id, Model viewModel) {
        viewModel.addAttribute("title", "Dettagli articolo");
        viewModel.addAttribute("article", articleService.read(id));
        return "article/detail";
    }
}