package com.novprod.lacronaca.services;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import com.novprod.lacronaca.Repositories.ArticleRepository;
import com.novprod.lacronaca.Repositories.UserRepository;
import com.novprod.lacronaca.dtos.ArticleDto;
import com.novprod.lacronaca.models.Article;
import com.novprod.lacronaca.models.Category;
import com.novprod.lacronaca.models.User;

@Service
public class ArticleService implements CrudService<ArticleDto, Article, Long> {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private ArticleRepository articleRepository;
    @Autowired
    private ImageService imageService;

    @Override
    public List<ArticleDto> readAll() {
        List<ArticleDto> dtos = new ArrayList<ArticleDto>();
        for (Article article : articleRepository.findAll()) {
            dtos.add(modelMapper.map(article, ArticleDto.class));
        }
        return dtos;
    }

    @Override
    public ArticleDto read(Long key) {
        Optional<Article> optArticle = articleRepository.findById(key);
        if (optArticle.isPresent()) {
            return modelMapper.map(optArticle.get(), ArticleDto.class);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Autore id=" + key + "non trovato");
        }
    }

    @Override
    public ArticleDto create(Article article, Principal principal, MultipartFile file) {
        String url = "";
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
            User user = (userRepository.findById(userDetails.getId())).get();
            article.setUser(user);
        }
        if (!file.isEmpty()) {
            try {
                CompletableFuture<String> futureUrl = imageService.saveImageOnCloud(file);
                url = futureUrl.get();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        article.setIsAccepted(null);
        ArticleDto dto = modelMapper.map(articleRepository.save(article), ArticleDto.class);
        // if (!file.isEmpty() && url != null) {
        if (!file.isEmpty()) {
            imageService.saveImageOnDB(url, article);
        }
        return dto;
    }

    @Override
    public ArticleDto update(Long key, Article updatedArticle, Principal principal, MultipartFile file) {
        String url = "";
        // Controlla se l'articolo esiste
        if (articleRepository.existsById(key)) {
            // Assegna l'ID dell'articolo originale
            updatedArticle.setId(key);
            // Recupera l'articolo originale
            Article article = articleRepository.findById(key).get();
            // Imposta l'utente dell'articolo originale
            updatedArticle.setUser(article.getUser());

            // Controllo se è stato caricato un nuovo file
            if (!file.isEmpty()) {
                try {
                    // Elimina l'immagine precedente
                    imageService.deleteImage(article.getImage().getPath());

                    try {
                        // Salva la nuova immagine
                        CompletableFuture<String> futureUrl = imageService.saveImageOnCloud(file);
                        url = futureUrl.get();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    // Salva il nuovo path nel database
                    imageService.saveImageOnDB(url, updatedArticle);

                    // L'articolo modificato torna in revisione
                    updatedArticle.setIsAccepted(null);

                    return modelMapper.map(articleRepository.save(updatedArticle), ArticleDto.class);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (article.getImage() == null) {
                // L'articolo originale non ha immagine e non è stata caricata
                updatedArticle.setIsAccepted(article.getIsAccepted());
            } else {
                // L'immagine non è modificata, mantieni quella originale
                updatedArticle.setImage(article.getImage());

                // Controlla se l'articolo è cambiato
                if (updatedArticle.equals(article) == false) {
                    updatedArticle.setIsAccepted(null);
                } else {
                    updatedArticle.setIsAccepted(article.getIsAccepted());
                }
                return modelMapper.map(articleRepository.save(updatedArticle), ArticleDto.class);
            }

        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        return null;
    }

    @Override
    public void delete(Long key) {
        if (articleRepository.existsById(key)) {
            Article article = articleRepository.findById(key).get();

            try {
                String path = article.getImage().getPath();
                article.getImage().setArticle(null);
                imageService.deleteImage(path);
            } catch (Exception e) {
                e.printStackTrace();
            }
            articleRepository.deleteById(key);
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    public List<ArticleDto> searchByCategory(Category category) {
        List<ArticleDto> dtos = new ArrayList<ArticleDto>();
        for (Article article : articleRepository.findByCategory(category)) {
            dtos.add(modelMapper.map(article, ArticleDto.class));
        }
        return dtos;
    }

    public List<ArticleDto> searchByAuthor(User user) {
        List<ArticleDto> dtos = new ArrayList<ArticleDto>();
        for (Article article : articleRepository.findByUser(user)) {
            dtos.add(modelMapper.map(article, ArticleDto.class));
        }
        return dtos;
    }

    public void setIsAccepted(Boolean result, Long id) {
        Article article = articleRepository.findById(id).get();
        article.setIsAccepted(result);
        articleRepository.save(article);
    }

    public List<ArticleDto> search(String keyword) {
        List<ArticleDto> dtos = new ArrayList<ArticleDto>();
        for (Article article : articleRepository.search(keyword)) {
            dtos.add(modelMapper.map(article, ArticleDto.class));
        }
        return dtos;
    }

}
