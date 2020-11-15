package pl.bookstore.restapi.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Getter
@Setter
public class Book {

    @Id
    private String isbn;
    private String title;
    private String description;
    private short publication_date;
    private short edition;
    private BigDecimal price;
    private String author;

    @OneToMany(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "book_isbn", updatable = false, insertable = false)
    private List<Comment> comment;
}
