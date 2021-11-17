package pricecheckerby.parser.parsers;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import pricecheckerby.model.Domain;

@Service
public class AmdParser implements WebSiteParser {
    private final By goodsSelector = By.cssSelector("#main>table");
    private final By nextSelector = By.className("next");
    @Value("${name.amd}")
    private String webSiteName;
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
        WebElement priceElement = goodBlock.findElement(By.className("price"));
        String sPrice = priceElement.getText();
        int sPriceEnd = sPrice.indexOf('р');
        sPrice = sPrice.substring(0, sPriceEnd);
        float price = Float.parseFloat(sPrice);
        return price;
    }

    @Override
    public String parseUrl(WebElement goodBlock, Domain domain) {
        WebElement urlElement = goodBlock.findElement(By.className("h33"));
        String url = urlElement.getAttribute("href");
        url = url.replaceFirst(domain.getUrl(), "");
        return url;
    }

    @Override
    public String getPageUrl(String url, long page) {
        return url + "page/" + page + "/";
    }
}
