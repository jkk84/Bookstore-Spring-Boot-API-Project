package pl.bookstore.restapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.bookstore.restapi.model.AddressEntity;

import java.util.List;


@Repository
public interface AddressRepository extends JpaRepository<AddressEntity, Long> {


    List<AddressEntity> findByCustomerEntityLogin(String login);

}
