package pricecheckerby.parser.parsers;

import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import pricecheckerby.model.Domain;

@Slf4j
@Service
public class VekParser implements WebSiteParser {
    private final By goodsSelector = By.className("result__item");
    private final By nextSelector = By.cssSelector("a[rel=\"next\"]");
    private final String webSiteName = "21vek";
    @Override
    public String getWebSiteName() {
        return webSiteName;
    }
    @Override
    public By getGoodsSelector() {
        return goodsSelector;
    }
    @Override
    public By getNextSelector() {
        return nextSelector;
    }
    @Override
    public float parsePrice(WebElement goodBlock) throws NumberFormatException {
        WebElement priceElement = goodBlock.findElement(By.cssSelector("span[data-price]"));
        String sPrice = priceElement.getAttribute("data-price");
        float price = Float.parseFloat(sPrice);
        return price;
    }
    @Override
    public String parseUrl(WebElement goodBlock, Domain domain) {
        WebElement urlElement = goodBlock.findElement(By.className("result__link"));
        String fullUrl = urlElement.getAttribute("href");
        String url = fullUrl.replaceFirst(domain.getUrl(), "");
        return url;
    }
    @Override
    public String getPageUrl(String url, long page) {
        String pageUrl = url + "/page:" + page;
        return pageUrl;
    }
}
