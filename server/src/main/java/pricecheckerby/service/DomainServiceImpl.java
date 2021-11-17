package pricecheckerby.service;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.SessionFactory;
import pricecheckerby.model.Domain;
import pricecheckerby.repository.DomainRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pricecheckerby.repository.CatalogUrlRepository;

import javax.persistence.EntityManagerFactory;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Slf4j
public class DomainServiceImpl implements DomainService {
    @Autowired
    private DomainRepository repository;
    @Override
    public List<Domain> findAll() {
        return (List<Domain>) repository.findAll();
    }

    @Override
    public Optional<Domain> getByName(String name) {
        return repository.getByName(name);
    }

    @Override
    public Optional<Domain> getByUrl(String url) {
        return repository.getByUrl(url);
    }
    @Override
    public Domain save(Domain domain) {
        return repository.save(domain);
    }
}
