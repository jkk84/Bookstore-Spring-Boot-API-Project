package pl.bookstore.restapi.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.bookstore.restapi.exception.NotFoundException;
import pl.bookstore.restapi.model.CategoryEntity;
import pl.bookstore.restapi.repository.CategoryRepository;
import pl.bookstore.restapi.service.CategoryService;
import pl.bookstore.restapi.util.Const;
import pl.bookstore.restapi.util.Deleted;

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
                .orElseThrow(() -> new NotFoundException(Const.CATEGORY, categoryId));
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
                .orElseThrow(() -> new NotFoundException(Const.CATEGORY, categoryId)));
    }

    @Override
    public Optional<String> deleteCategory(long categoryId) {
        CategoryEntity categoryEntity = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new NotFoundException(Const.CATEGORY, categoryId));
        categoryRepository.delete(categoryEntity);
        return Optional.of(Deleted.msg(Const.CATEGORY, categoryId));
    }
}
