package pricecheckerby.service;

import pricecheckerby.model.Domain;
import pricecheckerby.model.Product;

import java.util.Optional;

public interface ProductService {
    Optional<Product> getByDomainAndUrl(Domain domain, String url);
    Optional<Product> save(Product product);
    Optional<Product> findByFullUrl(String url);
}
