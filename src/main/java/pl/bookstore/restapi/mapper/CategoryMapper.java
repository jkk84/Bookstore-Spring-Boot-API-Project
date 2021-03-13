package pl.bookstore.restapi.mapper;

import org.springframework.stereotype.Service;
import pl.bookstore.restapi.model.CategoryEntity;
import pl.bookstore.restapi.model.dto.CategoryDto;


@Service
public class CategoryMapper {

    public CategoryEntity toEntity(CategoryDto categoryDto) {
        CategoryEntity categoryEntity = new CategoryEntity();
        categoryEntity.setName(categoryDto.getName());
        return categoryEntity;
    }
}
