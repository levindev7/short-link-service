package org.sls.shortlinkservice.controller;

import lombok.extern.slf4j.Slf4j;
import org.sls.shortlinkservice.service.UrlService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

@RestController
@RequestMapping
@Slf4j
public class RedirectController {
    private final UrlService service;

    public RedirectController(UrlService service) {
        this.service = service;
    }

    @GetMapping(path = "/{token}")
    RedirectView redirect(@PathVariable String token) {
        log.info("Processing a GET request for redirection from a short URL to the original URL and.");
        return new RedirectView(service.returnOriginalUrlForRedirect(token));
    }
}
