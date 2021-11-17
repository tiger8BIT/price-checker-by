package pricecheckerby.parser.service;

import pricecheckerby.model.CatalogUrl;
import pricecheckerby.model.Domain;
import pricecheckerby.parser.parsers.WebSiteParser;

public interface PagesParserService {
    void parsePages(final Domain domain, final CatalogUrl catalogUrl, final WebSiteParser webSiteParser);
}
