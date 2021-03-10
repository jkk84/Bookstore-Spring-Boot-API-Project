package pl.bookstore.restapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.bookstore.restapi.model.OrderEntity;

import java.util.List;


@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, Long> {

    boolean existsByBookEntityBookIdAndPurchaseEntityPurchaseId(long bookId, long purchaseId);

    List<OrderEntity> findByPurchaseEntityPurchaseId(long purchaseId);
}
