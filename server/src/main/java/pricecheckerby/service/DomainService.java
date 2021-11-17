package pricecheckerby.service;

import pricecheckerby.model.Domain;

import java.util.List;
import java.util.Optional;

public interface DomainService {
    List<Domain> findAll();
    Optional<Domain> getByName(String name);
    Optional<Domain> getByUrl(String url);
    Domain save(Domain domain);
}
