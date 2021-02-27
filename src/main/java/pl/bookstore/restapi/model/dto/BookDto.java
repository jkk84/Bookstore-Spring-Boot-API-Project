package pl.bookstore.restapi.model.dto;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookDto {

    private long bookId;

    private String isbn;

    private String title;

    private String description;

    private LocalDateTime createdAt;

    private BigDecimal price;

    private String imageUrl;

    private Set<Long> authorIds;

    private Set<Long> categoryIds;
}
