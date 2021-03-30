package pl.bookstore.restapi.mapper;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.bookstore.restapi.model.CategoryEntity;
import pl.bookstore.restapi.model.dto.CategoryDto;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CategoryMapperTest {

    @InjectMocks
    CategoryMapper categoryMapper;

    @Test
    void whenCallToEntityThenReturnCategoryEntity() {
        CategoryDto categoryDto = mock(CategoryDto.class);
        String name = "category";

        when(categoryDto.getName()).thenReturn(name);

        CategoryEntity result = categoryMapper.toEntity(categoryDto);
        assertEquals(name, result.getName());
    }
}