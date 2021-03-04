package pl.bookstore.restapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.bookstore.restapi.model.ReviewEntity;
import pl.bookstore.restapi.model.dto.ReviewDto;

import java.util.List;


@Repository
public interface ReviewRepository extends JpaRepository<ReviewEntity, Long> {

    List<ReviewEntity> findByBookEntityBookId(long bookId);

    List<ReviewEntity> findByCustomerEntityCustomerId(long customerId);

    List<ReviewEntity> findFirst5ByOrderByReviewIdDesc();

    boolean existsByCustomerEntityCustomerIdAndBookEntityBookId(long customerId, long bookId);
}
