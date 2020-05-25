package org.sls.shortlinkservice.service;

import lombok.extern.slf4j.Slf4j;
import org.sls.shortlinkservice.exception.NotFoundException;
import org.sls.shortlinkservice.exception.TokenTimeoutException;
import org.sls.shortlinkservice.model.Url;
import org.sls.shortlinkservice.repository.UrlRepository;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.Optional;

@Service
@Slf4j
public class UrlService  {
    private final UrlRepository repository;

    public UrlService(UrlRepository repository) {
        this.repository = repository;
    }

    public HashidsUtil hashidsUtil;

    public String notNullUrl;

    public String createShortUrl(String originalUrl) {
        Optional<Url> optionalUrl = Optional.ofNullable(repository.findByOriginalUrl(originalUrl));
        optionalUrl.ifPresent(url -> notNullUrl = url.getOriginalUrl());

        if(notNullUrl != null &&
            checkingRelevanceToken(repository.findByOriginalUrl(originalUrl).getToken())) {
            log.info("Original link is in the DB and the token has not expired, return short URL from DB.");
            return repository.findByOriginalUrl(originalUrl).getToken();
        }
        else if(notNullUrl != null &&
                checkingRelevanceToken(repository.findByOriginalUrl(originalUrl).getToken()) == false) {
            repository.save(HashidsUtil.getHashidsUtilWithNewId(originalUrl));
            log.info("Original link is in the DB and the token has expired, save new short URL to DB and return.");
            return HashidsUtil.getHashidsUtilWithNewId(originalUrl).getToken();
        }
        else {
                repository.save(HashidsUtil.getHashidsUtil(originalUrl));
            log.info("Original link was not previously in the database, save new short link to DB and return.");
            return HashidsUtil.getHashidsUtil(originalUrl).getToken();
        }
    }

    public String returnOriginalUrl(String token) throws NotFoundException {
        if(token.equals(repository.findByToken(token).getToken())) {
            log.info("Search the original URL by token and return");
            return repository.findByToken(token).getOriginalUrl();
        } else {
            log.error("404 Page Not Found");
            throw new NotFoundException();
        }
    }

    public String returnOriginalUrlForRedirect(String token) throws TokenTimeoutException, NotFoundException {
        if(token.equals(repository.findByToken(token).getToken()) &&
                checkingRelevanceToken(token)) {
            log.info("Search the original URL by token and checking the token expiration time and redirect to the original URL");
            return repository.findByToken(token).getOriginalUrl();
        }
        else if (token.equals(repository.findByToken(token).getToken()) &&
                checkingRelevanceToken(token) == false) {
            log.error("419 The token's expiration time has ended");
            throw new TokenTimeoutException();
        }
        else {
            log.error("404 Page Not Found");
            throw new NotFoundException();
        }
    }

    public boolean checkingRelevanceToken(String token) {
        Date currentTime = new Date();
        log.info("Creating a time reference point for comparison with the time of token creation and comparing them");
        return repository.findByToken(token).getUrlCreationTime().
                getTime() + 600000 > currentTime.getTime();
    }
}
