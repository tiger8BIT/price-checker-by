package pricecheckerby.repository;

import pricecheckerby.model.Domain;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DomainRepository extends CrudRepository<Domain, Long> {
    Optional<Domain> getByUrl(String url);
    Optional<Domain> getByName(String name);
}
