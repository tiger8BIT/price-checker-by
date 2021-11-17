package pricecheckerby.parser.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pricecheckerby.model.CatalogUrl;
import pricecheckerby.model.Domain;
import pricecheckerby.parser.parsers.WebSiteParser;
import pricecheckerby.service.CatalogUrlService;
import pricecheckerby.service.DomainService;

import java.util.List;

@Service
public class CatalogParserServiceImpl implements CatalogParserService {
    @Autowired
    private PagesParserService pagesParserService;
    @Autowired
    private CatalogUrlService catalogUrlService;
    @Autowired
    private DomainService domainService;
    @Override
    public void parse(WebSiteParser webSiteParser) {
        List<CatalogUrl> catalogUrls = catalogUrlService.findByName(webSiteParser.getWebSiteName());
        final Domain domain = domainService.getByName(webSiteParser.getWebSiteName()).orElseThrow();
        catalogUrls.forEach((catalogUrl) ->
                pagesParserService.parsePages(domain, catalogUrl, webSiteParser));
    }
}
