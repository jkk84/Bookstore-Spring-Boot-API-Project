package pl.bookstore.restapi.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;


@Entity
@Getter
@Setter
@Table(name = "books")
public class BookEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long bookId;

    private String isbn;

    private String title;

    private String description;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    private BigDecimal price;

    private String imageUrl;

    @ManyToMany
    @JoinTable(name="books_authors", joinColumns = @JoinColumn(name = "books_bookId"),
            inverseJoinColumns = @JoinColumn(name = "authors_authorId"))
    private Set<AuthorEntity> authorEntities;

    @ManyToMany
    @JoinTable(name="books_categories", joinColumns = @JoinColumn(name = "books_bookId"),
            inverseJoinColumns = @JoinColumn(name = "categories_authorId"))
    private Set<CategoryEntity> categoryEntities;
}
