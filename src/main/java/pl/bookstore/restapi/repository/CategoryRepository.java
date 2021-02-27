package pl.bookstore.restapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.bookstore.restapi.model.CategoryEntity;

import java.util.Iterator;
import java.util.Set;


@Repository
public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {

}
