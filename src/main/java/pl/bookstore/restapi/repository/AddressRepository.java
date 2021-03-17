package pl.bookstore.restapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.bookstore.restapi.model.AddressEntity;

import java.util.List;
import java.util.Optional;


@Repository
public interface AddressRepository extends JpaRepository<AddressEntity, Long> {


    List<AddressEntity> findByUserEntityLogin(String login);

    Optional<AddressEntity> findByAddressIdAndUserEntityLogin(long addressId, String login);

    void deleteByAddressIdAndUserEntityLogin(long addressId, String login);

    boolean existsByAddressIdAndUserEntityLogin(long addressId, String login);
}
