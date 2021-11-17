package pricecheckerby.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pricecheckerby.model.CatalogUrl;
import pricecheckerby.repository.CatalogUrlRepository;

import java.util.List;

@Service
public class CatalogUrlServiceImpl implements CatalogUrlService {
    @Autowired
    private CatalogUrlRepository catalogUrlRepository;
    public CatalogUrl save(CatalogUrl catalogUrl){
        return catalogUrlRepository.save(catalogUrl);
    }
    public List<CatalogUrl> findByName(String name){
        return catalogUrlRepository.findAllByDomainName(name);
    }
    public List<CatalogUrl> findAll(String name){
        return (List<CatalogUrl>) catalogUrlRepository.findAll();
    }
}
