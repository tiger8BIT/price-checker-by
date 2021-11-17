package pricecheckerby.controller.rest;

import lombok.extern.slf4j.Slf4j;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.openqa.selenium.By;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import pricecheckerby.model.*;
import pricecheckerby.service.DomainService;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping
@Slf4j
public class AvailableDomainsController {
    @Autowired
    private DomainService domainService;
    @Value("${name.wildberries}")
    private String wildberries;

    @CrossOrigin
    @GetMapping("/domains/url")
    public @ResponseBody
    ResponseEntity<List<String>>  availableDomain() {
        List<String> domains = domainService.findAll().stream().map(Domain::getUrl).collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.OK).body(domains);
    }
}
