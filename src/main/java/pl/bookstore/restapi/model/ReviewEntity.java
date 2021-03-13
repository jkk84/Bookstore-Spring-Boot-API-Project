package pl.bookstore.restapi.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;


@Entity
@Getter
@Setter
@Table(name = "reviews")
public class ReviewEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long reviewId;

    private long rating;

    private String comment;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinTable(name="reviews_users", joinColumns = @JoinColumn(name = "reviews_reviewId"),
            inverseJoinColumns = @JoinColumn(name = "users_userId"))
    private UserEntity userEntity;

    @ManyToOne
    @JoinTable(name="reviews_books", joinColumns = @JoinColumn(name = "reviews_reviewId"),
            inverseJoinColumns = @JoinColumn(name = "books_bookId"))
    private BookEntity bookEntity;
}
