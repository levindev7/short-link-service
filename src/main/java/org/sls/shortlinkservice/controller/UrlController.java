package org.sls.shortlinkservice.controller;

import org.sls.shortlinkservice.dto.CreateRequest;
import org.sls.shortlinkservice.dto.CreateResponse;
import org.sls.shortlinkservice.service.UrlService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import javax.validation.Valid;

@RestController
@RequestMapping("/create")
public class UrlController {
    private final UrlService service;

    public UrlController(UrlService service) {
        this.service = service;
    }

    @PostMapping
    CreateResponse createShortUrl(@RequestBody @Valid CreateRequest request) {
        return new CreateResponse(service.createShortUrl(request.getOriginalUrl())
        );
    }

    @GetMapping(path = "/getOriginalUrl/{token}")
    public String returnOriginalUrl(@PathVariable String token) {
        return service.returnOriginalUrl(token);
    }

    @GetMapping(path = "/{token}")
    RedirectView redirect(@PathVariable String token) {
        return new RedirectView(service.returnOriginalUrlForRedirect(token));
    }
}
