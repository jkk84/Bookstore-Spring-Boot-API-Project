package pl.bookstore.restapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.bookstore.restapi.model.PurchaseEntity;


@Repository
public interface PurchaseRepository extends JpaRepository<PurchaseEntity, Long> {

    PurchaseEntity findByCustomerEntityCustomerId(long customerId);

    boolean existsByCustomerEntityCustomerId(long customerId);
}
