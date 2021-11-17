package pricecheckerby.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pricecheckerby.exception.DomainNotFoundException;
import pricecheckerby.model.Domain;
import pricecheckerby.model.Price;
import pricecheckerby.model.Product;
import pricecheckerby.repository.ProductRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository repository;
    private final DomainService domainService;

    @Override
    public Optional<Product> getByDomainAndUrl(Domain domain, String url) {
        return repository.getByDomainAndUrl(domain, url);
    }

    @Override
    public Optional<Product> save(Product product) {
        if (!repository.existsByUrl(product.getUrl()))
            return Optional.of(repository.save(product));
        else
            return repository.getByUrl(product.getUrl());
    }
    @Override
    public Optional<Product> findByFullUrl(String url) {
        PriceService.FullUrl fullUrl = fromString(url);
        if(fullUrl == null) {
            throw new DomainNotFoundException();
        }
        return getByDomainAndUrl(fullUrl.getDomain(), fullUrl.getProductUrl());
    }
    private PriceService.FullUrl fromString(String fullUrl){
        if (fullUrl == null) return null;
        List<Domain> domains = domainService.findAll();
        for (Domain tDomain :
                domains) {
            if(fullUrl.startsWith(tDomain.getUrl())) {
                String productUrl = fullUrl.replaceFirst(tDomain.getUrl(), "");
                return new PriceService.FullUrl(tDomain, productUrl);
            }
        }
        return null;
    }
}
