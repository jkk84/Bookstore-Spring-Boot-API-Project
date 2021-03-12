package pl.bookstore.restapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.bookstore.restapi.model.ReviewEntity;

import java.util.List;


@Repository
public interface ReviewRepository extends JpaRepository<ReviewEntity, Long> {

    List<ReviewEntity> findByBookEntityBookId(long bookId);

    List<ReviewEntity> findFirst5ByOrderByReviewIdDesc();

    List<ReviewEntity> findByCustomerEntityLogin(String login);

    boolean existsByCustomerEntityLoginAndBookEntityBookId(String login, long bookId);
}
