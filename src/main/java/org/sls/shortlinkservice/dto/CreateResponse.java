package org.sls.shortlinkservice.dto;

import lombok.Value;
import lombok.extern.slf4j.Slf4j;

@Value
@Slf4j
public class CreateResponse {
    String link;

    public CreateResponse(String link) {
        this.link = "localhost:8080/" + link;
        log.info("");
    }

}
