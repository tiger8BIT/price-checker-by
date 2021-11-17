package pricecheckerby.controller.rest;

import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Data;
import pricecheckerby.controller.model.SearchParams;
import pricecheckerby.model.*;
import pricecheckerby.service.DomainService;
import pricecheckerby.service.PriceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import pricecheckerby.service.ProductService;

@RestController
@RequestMapping
@Slf4j
public class SearchController {
    @Autowired
    private PriceService priceService;
    @Autowired
    private DomainService domainService;
    @Autowired
    private ProductService productService;

    @CrossOrigin
    @PostMapping("/search")
    public @ResponseBody ResponseEntity<Object> search(@RequestBody SearchParams searchParams) {
        log.info("Search params: {}", searchParams);
        log.info("Search params: url = {} date = {}", searchParams.getUrl(), searchParams.getStartDate());
        Product product = productService.findByFullUrl(searchParams.getUrl())
                .orElseThrow(() -> new NoSuchElementException("Product is not found"));
        List<Price> priceList = priceService.findBy(product, searchParams.getStartDate());
        List<PriceData> searchResult = fromPriceList(priceList);
        if (searchResult.size() == 0) {
            log.info("Product is not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product is not found");
        } else {
            log.info("Response: {}", searchResult);
            return ResponseEntity.status(HttpStatus.OK).body(searchResult);
        }
    }
    private List<PriceData> fromPriceList(List<Price> priceList){
        return priceList.stream()
                .map(Price::getData)
                .sorted(Comparator.comparing(PriceData::getDate))
                .collect(Collectors.toList());
    }
}
