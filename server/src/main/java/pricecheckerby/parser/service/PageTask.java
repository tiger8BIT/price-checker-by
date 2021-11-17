package pricecheckerby.parser.service;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import pricecheckerby.model.Domain;
import pricecheckerby.model.Price;
import pricecheckerby.parser.parsers.WebSiteParser;
import pricecheckerby.service.PriceService;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
@Slf4j
class PageTask {
    private void parseGood(PriceService priceService, WebElement goodBlock, Domain domain, LocalDate date, WebSiteParser webSiteParser){
        try {
            float priceF = webSiteParser.parsePrice(goodBlock);
            String url = webSiteParser.parseUrl(goodBlock, domain);
            Price price = priceService.save(priceF, date.minusDays(3), domain, url);
            Price price1 = priceService.save(priceF, date.minusDays(1), domain, url);
            Price price2 = priceService.save(priceF, date.minusDays(2), domain, url);
            log.info("Item {} is saved", price);
        }
        catch (NumberFormatException e){
            log.error("Cant parse price");
        }
        catch (java.util.NoSuchElementException e){

        }
    }
    @NonNull
    final String link;
    public void run(PriceService priceService, WebDriver driver, Domain domain, LocalDate date, WebSiteParser webSiteParser) {
        driver.get(link);
        List<WebElement> elements = driver.findElements(webSiteParser.getGoodsSelector());
        elements.forEach(element -> parseGood(priceService, element, domain, date, webSiteParser));
    }
}
