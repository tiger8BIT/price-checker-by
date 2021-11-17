package pricecheckerby.parser.webdriver;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Configuration
public class WebDriverConfiguration {
    @Value("${path.driver}")
    private String driverPathName;
    @Autowired
    WebDriverService webDriverService;
    @Bean
    ChromeDriverService service() throws IOException {
        ChromeDriverService service;
        service = new ChromeDriverService.Builder()
                .usingDriverExecutable(new File(driverPathName))
                .usingAnyFreePort()
                .build();
        service.start();
        return service;
    }
    @Bean
    WebDriver driver(){
        return webDriverService.newWebDriver();
    }
    @Bean
    ExecutorService executorService(){
        return Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    }
}
