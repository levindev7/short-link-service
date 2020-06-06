package org.sls.shortlinkservice.dto;

import lombok.Value;
import lombok.extern.slf4j.Slf4j;

@Value
@Slf4j
public class CreateResponse {
    String link;

    public CreateResponse(String link) {
        this.link = link;
        log.info("Create response with a token: " + link);
    }
}
