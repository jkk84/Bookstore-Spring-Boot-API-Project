package pl.bookstore.restapi.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import pl.bookstore.restapi.exception.CategoryNotFoundException;
import pl.bookstore.restapi.model.CategoryEntity;
import pl.bookstore.restapi.repository.CategoryRepository;
import pl.bookstore.restapi.service.CategoryService;

import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    public List<CategoryEntity> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public Optional<CategoryEntity> getCategory(long categoryId) {
        CategoryEntity categoryEntity = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new CategoryNotFoundException(categoryId));
        return Optional.of(categoryEntity);
    }

    @Override
    public CategoryEntity addCategory(CategoryEntity categoryEntity) {
        return categoryRepository.save(categoryEntity);
    }

    @Override
    public Optional<CategoryEntity> updateCategory
            (CategoryEntity categoryEntity, long categoryId) {
        return Optional.of(categoryRepository.findById(categoryId)
                .map(category -> {
                    category.setName(categoryEntity.getName());
                    return categoryRepository.save(category);
                })
                .orElseThrow(() -> new CategoryNotFoundException(categoryId)));
    }

    @Override
    public void deleteCategory(long categoryId) {
        if(categoryRepository.existsById(categoryId)) {
            try {
                categoryRepository.deleteById(categoryId);
            } catch (Exception e){
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Delete Books-Category connections first.");
            }
        } else {
            throw  new CategoryNotFoundException(categoryId);
        }
    }
}
