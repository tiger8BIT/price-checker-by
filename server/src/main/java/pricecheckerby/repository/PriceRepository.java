package pricecheckerby.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import pricecheckerby.model.Price;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pricecheckerby.model.Product;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface PriceRepository extends CrudRepository<Price, Long> {
    List<Price> findByProductAndDateGreaterThanEqual(Product product, LocalDate date);
    boolean existsByDateLessThan(LocalDate date);
    Long deleteByDateLessThan(LocalDate date);
    List<Price> findAllByProductOrderByDateDesc(Product product, Pageable pageable);
}
