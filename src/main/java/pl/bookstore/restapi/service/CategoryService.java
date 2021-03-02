package pl.bookstore.restapi.service;

import pl.bookstore.restapi.model.CategoryEntity;

import java.util.List;
import java.util.Optional;


public interface CategoryService {

    List<CategoryEntity> getAllCategories();

    Optional<CategoryEntity> getCategory(long categoryId);

    CategoryEntity addCategory(CategoryEntity categoryEntity);

    Optional<CategoryEntity> updateCategory(CategoryEntity categoryEntity, long categoryId);

    Optional<String> deleteCategory(long categoryId);
}
