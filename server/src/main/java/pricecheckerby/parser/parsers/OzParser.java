package pricecheckerby.parser.parsers;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import pricecheckerby.model.Domain;

@Service
public class OzParser implements WebSiteParser {
    private final By goodsSelector = By.className("item-type-card");
    private final By nextSelector = By.cssSelector(".pg-next:not(.disabled)");
    private final String webSiteName = "oz";

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
        WebElement priceElement = goodBlock.findElement(By.className("item-type-card__btn"));
        String sPrice = priceElement.getText();
        sPrice = sPrice.trim();
        int sPriceEnd = sPrice.indexOf(' ');
        sPrice = sPrice.substring(0, sPriceEnd);
        sPrice = sPrice.replace(',', '.');
        float price = Float.parseFloat(sPrice);
        return price;
    }

    @Override
    public String parseUrl(WebElement goodBlock, Domain domain) {
        WebElement urlElement = goodBlock.findElement(By.className("item-type-card__link"));
        String url = urlElement.getAttribute("href");
        url = url.replaceFirst(domain.getUrl(), "");
        return url;
    }

    @Override
    public String getPageUrl(String url, long page) {
        return url + "?page=" + page;
    }
}
