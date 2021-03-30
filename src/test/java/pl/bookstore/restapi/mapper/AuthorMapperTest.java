package pl.bookstore.restapi.mapper;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.bookstore.restapi.model.AuthorEntity;
import pl.bookstore.restapi.model.dto.AuthorDto;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthorMapperTest {

    @InjectMocks
    AuthorMapper authorMapper;

    @Test
    void whenCallToEntityThenReturnAuthorEntity() {
        AuthorDto authorDto = mock(AuthorDto.class);
        String biography = "biography";
        String firstName = "John";
        String lastName = "Smith";

        when(authorDto.getBiography()).thenReturn(biography);
        when(authorDto.getFirstName()).thenReturn(firstName);
        when(authorDto.getLastName()).thenReturn(lastName);

        AuthorEntity result = authorMapper.toEntity(authorDto);
        assertEquals(biography, result.getBiography());
        assertEquals(firstName, result.getFirstName());
        assertEquals(lastName, result.getLastName());
    }
}