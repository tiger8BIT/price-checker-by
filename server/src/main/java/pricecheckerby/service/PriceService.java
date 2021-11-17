package pricecheckerby.service;

import lombok.AllArgsConstructor;
import lombok.Data;
import pricecheckerby.model.Domain;
import pricecheckerby.model.Price;
import pricecheckerby.model.Product;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface PriceService {
    List<Price> findAll();
    Price save(Price price);
    Price save(float price, LocalDate date, Domain domain, String url);
    Optional<Price> getLastPrice(Product product);
    List<Price> findBy(Product product, LocalDate date);
    void deleteByDateLessThanOneYearOldIfExists();
    @AllArgsConstructor
    @Data
    class FullUrl {
        Domain domain;
        String productUrl;
    }
}
