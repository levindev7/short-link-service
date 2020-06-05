package org.sls.shortlinkservice.controller;

import lombok.extern.slf4j.Slf4j;
import org.sls.shortlinkservice.service.UrlService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
@Slf4j
public class ReturnUrlController {
    private final UrlService service;

    public ReturnUrlController(UrlService service) {
        this.service = service;
    }

    @GetMapping(path = "/getOriginalUrl/{token}")
    public String returnOriginalUrl(@PathVariable String token) {
        log.info("Processing a GET request to return the original URL based on a token: " + token);
        return service.returnOriginalUrl(token);
    }
}
