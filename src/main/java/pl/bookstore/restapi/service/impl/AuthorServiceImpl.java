package pl.bookstore.restapi.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.bookstore.restapi.exception.NotFoundException;
import pl.bookstore.restapi.util.Const;
import pl.bookstore.restapi.model.AuthorEntity;
import pl.bookstore.restapi.repository.AuthorRepository;
import pl.bookstore.restapi.service.AuthorService;
import pl.bookstore.restapi.util.Deleted;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;

    @Override
    public List<AuthorEntity> getAllAuthors() {
        return authorRepository.findAll();
    }

    @Override
    public Optional<AuthorEntity> getAuthor(long authorId) {
        AuthorEntity authorEntity = authorRepository.findById(authorId)
                .orElseThrow(() -> new NotFoundException(Const.AUTHOR, authorId));
        return Optional.of(authorEntity);
    }

    @Override
    public AuthorEntity addAuthor(AuthorEntity authorEntity) {
        return authorRepository.save(authorEntity);
    }

    @Override
    public Optional<AuthorEntity> updateAuthor(AuthorEntity authorEntity,long authorId) {
        return Optional.of(authorRepository.findById(authorId)
                .map(author -> {
                    author.setFirstName(authorEntity.getFirstName());
                    author.setLastName(authorEntity.getLastName());
                    author.setBiography(authorEntity.getBiography());
                    return authorRepository.save(author);
                })
                .orElseThrow(() -> new NotFoundException(Const.AUTHOR, authorId)));
    }

    @Override
    public Optional<String> deleteAuthor(long authorId) {
        AuthorEntity authorEntity = authorRepository.findById(authorId)
                .orElseThrow(() -> new NotFoundException(Const.AUTHOR, authorId));
        authorRepository.delete(authorEntity);
        return Optional.of(Deleted.msg(Const.AUTHOR, authorId));
    }
}
