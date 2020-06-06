package org.sls.shortlinkservice.controller;

import lombok.extern.slf4j.Slf4j;
import org.sls.shortlinkservice.dto.CreateRequest;
import org.sls.shortlinkservice.dto.CreateResponse;
import org.sls.shortlinkservice.service.UrlService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/create")
@Slf4j
public class CreateUrlController {
    private final UrlService service;

    public CreateUrlController(UrlService service) {
        this.service = service;
    }

    @PostMapping
    public CreateResponse createShortUrl(@RequestBody @Valid CreateRequest request) {
        log.info("processing a POST request: " + request + " to create a short URL.");
        return new CreateResponse(service.createShortUrl(request.getOriginalUrl().trim())
        );
    }
}
