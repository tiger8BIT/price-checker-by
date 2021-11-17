package pricecheckerby.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pricecheckerby.model.Domain;
import pricecheckerby.model.Product;

import java.util.Optional;

@Repository
public interface ProductRepository extends CrudRepository<Product, Long> {
    Optional<Product> getByDomainAndUrl(Domain domain, String url);
    boolean existsByUrl(String url);
    Optional<Product> getByUrl(String url);
}
