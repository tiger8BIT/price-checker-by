package pricecheckerby;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import pricecheckerby.service.DomainService;

@SpringBootApplication
@EnableScheduling
public class Application {
    @Autowired
    private DomainService domainService;
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
