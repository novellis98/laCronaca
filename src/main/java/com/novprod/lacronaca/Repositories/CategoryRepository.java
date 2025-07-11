package com.novprod.lacronaca.Repositories;

import org.springframework.data.repository.ListCrudRepository;

import com.novprod.lacronaca.models.Category;

public interface CategoryRepository extends ListCrudRepository<Category, Long> {

    // Additional query methods can be defined here if needed
    // For example, to find categories by name:
    // List<Category> findByName(String name);

}
