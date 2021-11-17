package pricecheckerby.parser.service;

import pricecheckerby.model.CatalogUrl;
import pricecheckerby.parser.parsers.WebSiteParser;

import java.util.List;
import java.util.concurrent.BlockingQueue;

public interface PagesTaskService {
    List<PageTask> getPageTasks(CatalogUrl catalogUrl, WebSiteParser webSiteParser);
    BlockingQueue<PageTask> getBlockingQueue(CatalogUrl catalogUrl, WebSiteParser webSiteParser);
}
