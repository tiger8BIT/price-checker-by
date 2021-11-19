package pricecheckerby.parser.parsers;

import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import pricecheckerby.model.Domain;

@Slf4j
@Service
public class WildberriesParser implements WebSiteParser {
    private final By goodsSelector = By.className("product-card__main");
    private final By nextSelector = By.className("pagination__next");
    private final String webSiteName = "wildberries";

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
    public float parsePrice(WebElement element) throws NumberFormatException{
        String priceS = element.findElement(By.className("price-localized")).getText();
        priceS = priceS.replace("р.", ".");
        priceS = priceS.replace("к.", "");
        priceS = priceS.replace("(", "");
        priceS = priceS.replace(")", "");
        priceS = priceS.replace(" ", "");
        float priceF;
        priceF = Float.parseFloat(priceS);
        return priceF;
    }
    @Override
    public String parseUrl(WebElement element, Domain domain) {
        String url = element.getAttribute("href");
        url = url.replaceFirst(domain.getUrl(), "");
        return url;
    }
    @Override
    public String getPageUrl(String url, long page){
        return url + "?page=" + page;
    }
}
