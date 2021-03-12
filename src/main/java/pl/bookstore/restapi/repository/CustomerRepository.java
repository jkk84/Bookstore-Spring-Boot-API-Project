package pl.bookstore.restapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.bookstore.restapi.model.CustomerEntity;

import java.util.List;
import java.util.Optional;


@Repository
public interface CustomerRepository extends JpaRepository<CustomerEntity, Long> {

    Optional<CustomerEntity> findByLogin(String login);

    void deleteByLogin(String login);

    boolean existsByLoginAndAddressEntitiesAddressIdIn(String login, List<Long> addressId);

    boolean existsByLogin(String login);
}
