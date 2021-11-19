package pricecheckerby.parser.parsers;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import pricecheckerby.model.Domain;

@Service
public class LamodaParser implements WebSiteParser {
    private final By goodsSelector = By.className("products-list-item");
    private final By nextSelector = By.cssSelector(".paginator__next:not([style])");
    private final String webSiteName = "lamoda";
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
        String sPrice = goodBlock.getAttribute("data-price");
        float price = Float.parseFloat(sPrice);
        return price;
    }

    @Override
    public String parseUrl(WebElement goodBlock, Domain domain) {
        WebElement urlElement = goodBlock.findElement(By.className("products-list-item__link"));
        String url = urlElement.getAttribute("href");
        url = url.replaceFirst(domain.getUrl(), "");
        return url;
    }

    @Override
    public String getPageUrl(String url, long page) {
        return url + "?page=" + page;
    }
}
