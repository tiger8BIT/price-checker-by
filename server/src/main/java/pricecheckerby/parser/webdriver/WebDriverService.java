package pricecheckerby.parser.webdriver;

import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class WebDriverService {
    @Autowired
    private ChromeDriverService service;
    private final ChromeOptions chromeOptions = new ChromeOptions().addArguments("--headless", "--disable-dev-shm-usage", "--no-sandbox", "--disable-gpu", "--blink-settings=imagesEnabled=false", "--window-size=1920,1080");

    public WebDriver newWebDriver(){
        return new RemoteWebDriver(service.getUrl(), chromeOptions);
    }


}
