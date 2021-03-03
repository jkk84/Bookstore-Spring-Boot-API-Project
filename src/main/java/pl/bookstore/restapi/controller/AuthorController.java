package pl.bookstore.restapi.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.bookstore.restapi.model.AuthorEntity;
import pl.bookstore.restapi.service.AuthorService;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/authors")
public class AuthorController {

    private final AuthorService authorService;

    @GetMapping
    public List<AuthorEntity> getAllAuthors() {
        return authorService.getAllAuthors();
    }

    @GetMapping(params = {"authorId"})
    public ResponseEntity<AuthorEntity> getAuthor(@RequestParam long authorId) {
        return authorService.getAuthor(authorId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<AuthorEntity> addAuthor(@RequestBody AuthorEntity authorEntity) {
        return ResponseEntity.ok(authorService.addAuthor(authorEntity));
    }

    @PutMapping
    public ResponseEntity<AuthorEntity> updateAuthor
            (@RequestBody AuthorEntity authorEntity, @RequestParam long authorId) {
        return authorService.updateAuthor(authorEntity, authorId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping
    public void deleteAuthor(@RequestParam long authorId) {
        authorService.deleteAuthor(authorId);
    }
}
