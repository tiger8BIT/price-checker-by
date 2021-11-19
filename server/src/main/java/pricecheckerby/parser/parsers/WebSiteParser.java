package pricecheckerby.parser.parsers;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import pricecheckerby.model.Domain;

public interface WebSiteParser  {

    /**
     * @return website name that should be the same as name column value in domain table
     */
    String getWebSiteName();

    /**
     * @return selenium selector for card product element
     */
    By getGoodsSelector();

    /**
     * @return selenium selector for next page link or button
     */
    By getNextSelector();

    /**
     * Extract product price from card product element
     * @param goodBlock card product element
     * @return product price
     */
    float parsePrice(WebElement goodBlock) throws NumberFormatException;

    /**
     * Extract product url from card product element. Use domain param to remove it from url.
     * @param goodBlock card product element
     * @param domain domain entity
     * @return product url
     */
    String parseUrl(WebElement goodBlock, Domain domain);

    /**
     * Defines how to combine catalog url and page number into page url
     * @param url card product element
     * @param page domain entity
     * @return product url
     */
    String getPageUrl(String url, long page);
}
