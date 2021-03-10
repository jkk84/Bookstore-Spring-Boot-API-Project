package pl.bookstore.restapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.bookstore.restapi.model.CustomerEntity;

import java.util.List;


@Repository
public interface CustomerRepository extends JpaRepository<CustomerEntity, Long> {

    boolean existsByCustomerIdAndAddressEntitiesAddressIdIn(long customerId, List<Long> addressId);
}
