package pl.bookstore.restapi.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.bookstore.restapi.model.dto.ReviewDto;
import pl.bookstore.restapi.service.ReviewService;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping
    public ResponseEntity<ReviewDto> addReview(@RequestBody ReviewDto reviewDto) {
        return reviewService.addReview(reviewDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.badRequest().build());
    }

    @GetMapping(params = {"bookId"})
    public List<ReviewDto> getBookReviews(@RequestParam long bookId) {
        return reviewService.getBookReviews(bookId);
    }

    @GetMapping(params = {"customerId"})
    public List<ReviewDto> getCustomerReviews(@RequestParam String login) {
        return reviewService.getCustomerReviews(login);
    }

    @GetMapping
    public List<ReviewDto> getLatestFiveReviews() {
        return reviewService.getLatestFiveReviews();
    }

    @PutMapping
    public ResponseEntity<ReviewDto> updateReview
            (@RequestBody ReviewDto reviewDto, @RequestParam long reviewId) {
        return reviewService.updateReview(reviewDto, reviewId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping
    public void deleteReview(@RequestParam long reviewId) {
        reviewService.deleteReview(reviewId);
    }
}
