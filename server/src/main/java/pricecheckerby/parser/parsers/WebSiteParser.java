package pricecheckerby.parser.parsers;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import pricecheckerby.model.Domain;

public interface WebSiteParser  {
    String getWebSiteName();
    By getGoodsSelector();
    By getNextSelector();
    float parsePrice(WebElement goodBlock) throws NumberFormatException;
    String parseUrl(WebElement goodBlock, Domain domain);
    String getPageUrl(String url, long page);
}
