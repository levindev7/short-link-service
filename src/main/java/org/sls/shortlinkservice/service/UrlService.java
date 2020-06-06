package org.sls.shortlinkservice.service;

import lombok.extern.slf4j.Slf4j;
import org.sls.shortlinkservice.exception.NotFoundException;
import org.sls.shortlinkservice.exception.TokenTimeoutException;
import org.sls.shortlinkservice.model.Url;
import org.sls.shortlinkservice.repository.UrlRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Date;

@Service
@Slf4j
public class UrlService {
    private final UrlRepository repository;

    public UrlService(UrlRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public String createShortUrl(String originalUrl) {
        Url url = repository.findByOriginalUrl(originalUrl);
        if (url == null) {
            url = HashidsUtil.getHashidsUtil(originalUrl);
            repository.save(url);
            log.info(originalUrl + " was not previously in the database, save new short link to DB and return.");
            return url.getToken();
        }
        if (checkingRelevanceToken(url)) {
            log.info(originalUrl + " is in the DB and the token: " + url.getToken() + " has not expired, return short URL from DB.");
            return url.getToken();
        } else {
            url = HashidsUtil.getHashidsUtilWithNewId(originalUrl);
            repository.save(url);
            log.info(originalUrl + " is in the DB and the token has expired, save new short URL to DB and return.");
            return url.getToken();
        }
    }

    public String returnOriginalUrl(String token) {
        Url url = repository.findByToken(token);
        if (url == null) {
            log.error("404 Page Not Found, token " + token + " is missing from the DB");
            throw new NotFoundException();
        } else {
            log.info("Search the original URL by " + token + " and return");
            return url.getOriginalUrl();
        }
    }

    public String returnOriginalUrlForRedirect(String token) {
        Url url = repository.findByToken(token);
        if (url == null) {
            log.error("404 Page Not Found, token " + token + " is missing from the DB");
            throw new NotFoundException();
        } else if (checkingRelevanceToken(url)) {
            log.info("Search the original URL by token: " + token + " and checking the token expiration time and redirect to the original URL");
            return url.getOriginalUrl();
        } else {
            log.error("419 The token's: " + token + " expiration time has ended");
            throw new TokenTimeoutException();
        }
    }

    private boolean checkingRelevanceToken(Url url) {
        Date currentTime = new Date();
        int lifeTimeOfToken = 600000; // in milliseconds, 10 minutes right now
        log.info("Creating a time reference point for comparison with the time of token (" + url.getToken() + ") creation and comparing them");

        return url.getUrlCreationTime().getTime() + lifeTimeOfToken > currentTime.getTime();
    }
}
