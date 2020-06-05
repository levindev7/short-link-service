package org.sls.shortlinkservice.dto;

import lombok.Value;
import lombok.extern.slf4j.Slf4j;

@Value
@Slf4j
public class CreateResponse {
    String token;
    String host = "localhost:8080/";

    /*
    Need to get the domain using the method -
    InetAddress address = InetAddress.getLocalHost();
    String domain = address.getCanonicalHostName();*/

    public CreateResponse(String token) {
        this.token = host + token;
        log.info("Create response with a token: " + token);
    }
}
