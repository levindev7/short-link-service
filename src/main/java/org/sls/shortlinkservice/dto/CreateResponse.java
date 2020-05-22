package org.sls.shortlinkservice.dto;

import lombok.Value;

@Value
public class CreateResponse {
    private final String link;

    public CreateResponse(String link) {
        this.link = "/" + link;
    }
}
