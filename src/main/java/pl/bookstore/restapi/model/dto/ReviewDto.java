package pl.bookstore.restapi.model.dto;

import lombok.*;
import java.time.LocalDateTime;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReviewDto {

    private long reviewId;

    private long rating;

    private String comment;

    private LocalDateTime createdAt;

    private String login;

    private long bookId;
}
