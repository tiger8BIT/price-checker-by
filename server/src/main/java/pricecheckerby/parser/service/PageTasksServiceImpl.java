package pricecheckerby.parser.service;

import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pricecheckerby.model.CatalogUrl;
import pricecheckerby.parser.parsers.WebSiteParser;
import pricecheckerby.service.CatalogUrlService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

@Service
@Slf4j
public class PageTasksServiceImpl implements PagesTaskService {
    @Autowired
    private WebDriver driver;
    @Autowired
    private CatalogUrlService catalogUrlService;
    private List<PageTask> lastPageNotFound(CatalogUrl catalogUrl, WebSiteParser webSiteParser){
        List<PageTask> pageTasks = new ArrayList<>();
        long page = 1;
        for(;true; page++) {
            String link = webSiteParser.getPageUrl(catalogUrl.getUrl(), page);
            driver.get(link);
            try {
                driver.findElement(webSiteParser.getNextSelector());
                log.info("Page [{}] is exists", page);
                pageTasks.add(new PageTask(link));
            }
            catch (NoSuchElementException e) {
                if(page == 1) {
                    pageTasks.add(new PageTask(link));
                }
                break;
            }
        }
        log.info("Last page is [{}]", page);
        catalogUrl.setLastPage(page);
        catalogUrlService.save(catalogUrl);
        return pageTasks;
    }

    private void findElement(WebDriver driver, String url, By selector) throws NoSuchElementException{
        driver.get(url);
        driver.findElement(selector);
    }

    private List<PageTask> lastPageIsFound(CatalogUrl catalogUrl, WebSiteParser webSiteParser){
        List<PageTask> pageTasks = new ArrayList<>(catalogUrl.getLastPage().intValue());
        String link = webSiteParser.getPageUrl(catalogUrl.getUrl(), catalogUrl.getLastPage());
        driver.get(link);
        try {
            driver.findElement(webSiteParser.getNextSelector());
            log.info("There is new pages in catalog {}", catalogUrl.getUrl());
            {
                long newLastPage = catalogUrl.getLastPage();
                try {
                    while (true)
                        findElement(driver, webSiteParser.getPageUrl(catalogUrl.getUrl(),++newLastPage), webSiteParser.getNextSelector());
                } catch (NoSuchElementException e) {
                    log.info("Last page is changed from [{}] to [{}]", catalogUrl.getLastPage(), newLastPage);
                    catalogUrl.setLastPage(newLastPage);
                    catalogUrlService.save(catalogUrl);
                }
            }
        }
        //не найдено
        catch (NoSuchElementException e) {
            long newLastPage = catalogUrl.getLastPage();
            while (true) {
                try {
                    findElement(driver,webSiteParser.getPageUrl(catalogUrl.getUrl(), newLastPage - 1), webSiteParser.getNextSelector());
                    break;
                } catch (NoSuchElementException ex) {
                    if(newLastPage == 1) break;
                    newLastPage--;
                }
            }
            if (newLastPage != catalogUrl.getLastPage()) {
                log.info("Last page is changed from [{}] to [{}]", catalogUrl.getLastPage(), newLastPage);
                catalogUrl.setLastPage(newLastPage);
                catalogUrlService.save(catalogUrl);
            } else {
                log.info("Last page has not changed");
            }
        }
        for (long page = 1; page <= catalogUrl.getLastPage(); page++) {
            pageTasks.add(new PageTask(webSiteParser.getPageUrl(catalogUrl.getUrl(), page)));
        }
        return pageTasks;
    }
    @Override
    public List<PageTask> getPageTasks(CatalogUrl catalogUrl, WebSiteParser webSiteParser){
        Optional<Long> lastPage = Optional.ofNullable(catalogUrl.getLastPage());
        if(lastPage.isEmpty()){
            log.info("Last page is not found in data base");
            return lastPageNotFound(catalogUrl, webSiteParser);
        } else {
            log.info("Last page is found in data base");
            return lastPageIsFound(catalogUrl, webSiteParser);
        }
    }
    @Override
    public BlockingQueue<PageTask> getBlockingQueue(CatalogUrl catalogUrl, WebSiteParser webSiteParser) {
        final List<PageTask> pageTasks = getPageTasks(catalogUrl, webSiteParser);
        final BlockingQueue<PageTask> queue = new ArrayBlockingQueue<>(pageTasks.size());
        pageTasks.forEach((task) -> {
            try {
                queue.put(task);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        return queue;
    }
}
