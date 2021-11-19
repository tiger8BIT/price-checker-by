package pricecheckerby.parser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import pricecheckerby.parser.parsers.WebSiteParser;
import pricecheckerby.parser.service.CatalogParserService;

import java.util.Map;

@Service
public class ParsersExecutorServiceImpl implements ParsersExecutorService {
    @Autowired
    private CatalogParserService catalogParserService;
    @Autowired
    private ApplicationContext applicationContext;
    @Override
    public void execute(){
        Map<String, WebSiteParser> siteParserMap = applicationContext.getBeansOfType(WebSiteParser.class);
        siteParserMap.values().forEach(parser -> catalogParserService.parse(parser));
    }
}
