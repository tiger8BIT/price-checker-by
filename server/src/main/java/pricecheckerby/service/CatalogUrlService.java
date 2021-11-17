package pricecheckerby.service;

import pricecheckerby.model.CatalogUrl;

import java.util.List;

public interface CatalogUrlService {
    CatalogUrl save(CatalogUrl catalogUrl);
    List<CatalogUrl> findByName(String name);
    List<CatalogUrl> findAll(String name);
}
