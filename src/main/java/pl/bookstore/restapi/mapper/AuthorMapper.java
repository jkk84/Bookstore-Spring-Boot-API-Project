package pl.bookstore.restapi.mapper;

import org.springframework.stereotype.Service;
import pl.bookstore.restapi.model.AuthorEntity;
import pl.bookstore.restapi.model.dto.AuthorDto;


@Service
public class AuthorMapper {

    public AuthorEntity toEntity(AuthorDto authorDto) {
        AuthorEntity authorEntity = new AuthorEntity();
        authorEntity.setBiography(authorDto.getBiography());
        authorEntity.setFirstName(authorDto.getFirstName());
        authorEntity.setLastName(authorDto.getLastName());
        return authorEntity;
    }
}
