package pl.bookstore.restapi.service;

import pl.bookstore.restapi.model.CategoryEntity;
import pl.bookstore.restapi.model.dto.CategoryDto;

import java.util.List;
import java.util.Optional;


public interface CategoryService {

    List<CategoryEntity> getAllCategories();

    Optional<CategoryEntity> getCategory(long categoryId);

    CategoryEntity addCategory(CategoryDto categoryDto);

    Optional<CategoryEntity> updateCategory(CategoryDto categoryDto, long categoryId);

    void deleteCategory(long categoryId);
}
