package pricecheckerby.service;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import pricecheckerby.model.Domain;
import pricecheckerby.model.Price;
import pricecheckerby.model.Product;
import pricecheckerby.repository.PriceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class PriceServiceImpl implements PriceService {
    @Autowired
    private PriceRepository priceRepository;
    @Autowired ProductService productService;
    @Autowired DomainService domainService;

    @Override
    public List<Price> findAll() {
        return (List<Price>) priceRepository.findAll();
    }

    @Override
    public Price save(Price price) {
        return priceRepository.save(price);
    }

    @Override
    public Price save(float priceF, LocalDate date, Domain domain, String url) {
        Product product = new Product(url, domain);
        product = productService.save(product).orElseThrow(() -> new java.util.NoSuchElementException(""));;
        Price price = new Price(priceF, date, product);
        return priceRepository.save(price);
    }

    @Override
    public Optional<Price> getLastPrice(Product product) {
        return priceRepository.findAllByProductOrderByDateDesc(product, PageRequest.of(0,1)).stream().findFirst();
    }

    @Override
    public List<Price> findBy(Product product, LocalDate date) {
        return priceRepository.findByProductAndDateGreaterThanEqual(product, date);
    }

    private LocalDate getLastYear(){
        return LocalDate.now().minusYears(1);
    }

    @Override
    public void deleteByDateLessThanOneYearOldIfExists() {
        LocalDate lastYear = getLastYear();
        boolean exists = priceRepository.existsByDateLessThan(lastYear);
        if (exists) {
            long deletedCount = priceRepository.deleteByDateLessThan(lastYear);
            log.info("Deleted prices: {}", deletedCount);
        }
    }
}
