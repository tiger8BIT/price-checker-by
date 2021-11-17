package pricecheckerby.repository;

import org.springframework.data.repository.CrudRepository;
import pricecheckerby.model.CatalogUrl;

import java.util.List;

public interface CatalogUrlRepository extends CrudRepository<CatalogUrl, String> {
    List<CatalogUrl> findAllByDomainName(String name);
}
