package pricecheckerby.parser.service;

import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pricecheckerby.model.CatalogUrl;
import pricecheckerby.model.Domain;
import pricecheckerby.parser.webdriver.WebDriverService;
import pricecheckerby.parser.parsers.WebSiteParser;
import pricecheckerby.service.DomainService;
import pricecheckerby.service.PriceService;

import java.time.LocalDate;
import java.util.Collections;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;

@Service
@Slf4j
public class PagesParserServiceImpl implements PagesParserService {
    @Autowired
    private ExecutorService executorService;
    @Autowired
    private WebDriverService webDriverService;
    @Autowired
    private PagesTaskService pagesTaskService;
    @Autowired
    private PriceService priceService;
    @Override
    public void parsePages(final Domain domain, final CatalogUrl catalogUrl, final WebSiteParser webSiteParser){
        log.info("Loading prices from {} is started", webSiteParser.getWebSiteName());
        final LocalDate date = LocalDate.now();
        final BlockingQueue<PageTask> queue = pagesTaskService.getBlockingQueue(catalogUrl, webSiteParser);
        Callable<Void> callable = () -> {
            WebDriver driver = webDriverService.newWebDriver();
            while (!queue.isEmpty() && !Thread.currentThread().isInterrupted()) {
                PageTask pageTask = queue.take();
                pageTask.run(priceService, driver, domain, date, webSiteParser);
            }
            driver.quit();
            return null;
        };
        try {
            executorService.invokeAll(Collections.nCopies(Runtime.getRuntime().availableProcessors(), callable));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info("Loading prices from {} is finished", webSiteParser.getWebSiteName());
    }
}
