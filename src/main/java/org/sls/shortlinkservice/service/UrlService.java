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
public class UrlService {
    private final UrlRepository repository;

    public UrlService(UrlRepository repository) {
        this.repository = repository;
    }

    public String createShortUrl(String originalUrl) {
        if (repository.findByOriginalUrl(originalUrl) == null) {
            repository.save(HashidsUtil.getHashidsUtil(originalUrl));
            log.info("Original link was not previously in the database, save new short link to DB and return.");
            return HashidsUtil.getHashidsUtil(originalUrl).getToken();
        }
        if (checkingRelevanceToken(repository.findByOriginalUrl(originalUrl).getToken())) {
            log.info("Original link is in the DB and the token has not expired, return short URL from DB.");
            return repository.findByOriginalUrl(originalUrl).getToken();
        } else {
            repository.save(HashidsUtil.getHashidsUtilWithNewId(originalUrl));
            log.info("Original link is in the DB and the token has expired, save new short URL to DB and return.");
            return HashidsUtil.getHashidsUtilWithNewId(originalUrl).getToken();
        }
    }

    public String returnOriginalUrl(String token) {
        if (repository.findByToken(token) == null) {
            log.error("404 Page Not Found");
            throw new NotFoundException();
        } else {
            log.info("Search the original URL by token and return");
            return repository.findByToken(token).getOriginalUrl();
        }
    }

    public String returnOriginalUrlForRedirect(String token) {
        if (repository.findByToken(token) == null) {
            log.error("404 Page Not Found");
            throw new NotFoundException();
        } else if (checkingRelevanceToken(token)) {
            log.info("Search the original URL by token and checking the token expiration time and redirect to the original URL");
            return repository.findByToken(token).getOriginalUrl();
        } else {
            log.error("419 The token's expiration time has ended");
            throw new TokenTimeoutException();
        }
    }

    private boolean checkingRelevanceToken(String token) {
        Date currentTime = new Date();
        log.info("Creating a time reference point for comparison with the time of token creation and comparing them");
        return repository.findByToken(token).getUrlCreationTime().
                getTime() + 600000 > currentTime.getTime();
    }
}
