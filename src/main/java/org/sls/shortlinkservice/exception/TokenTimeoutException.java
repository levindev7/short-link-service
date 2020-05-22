package org.sls.shortlinkservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.FORBIDDEN)
public class TokenTimeoutException extends RuntimeException {
    public TokenTimeoutException() {
        super("The short URL's lifetime has ended");
    }
}
