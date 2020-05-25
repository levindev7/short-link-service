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
            return repository.findByOriginalUrl(originalUrl).getToken();
        }
        else if(notNullUrl != null &&
                checkingRelevanceToken(repository.findByOriginalUrl(originalUrl).getToken()) == false) {
            repository.save(HashidsUtil.getHashidsUtilWithNewId(originalUrl));
            return HashidsUtil.getHashidsUtilWithNewId(originalUrl).getToken();
        }
        else {
                repository.save(HashidsUtil.getHashidsUtil(originalUrl));
            return HashidsUtil.getHashidsUtil(originalUrl).getToken();
        }
    }

    public String returnOriginalUrl(String token) throws NotFoundException {
        if(token.equals(repository.findByToken(token).getToken())) {
            return repository.findByToken(token).getOriginalUrl();
        } else {
            log.error("404 Page Not Found");
            throw new NotFoundException();
        }
    }

    public String returnOriginalUrlForRedirect(String token) throws TokenTimeoutException, NotFoundException {
        if(token.equals(repository.findByToken(token).getToken()) &&
                checkingRelevanceToken(token)) {
            return repository.findByToken(token).getOriginalUrl();
        }
        else if (token.equals(repository.findByToken(token).getToken()) &&
                checkingRelevanceToken(token) == false) {
            log.error("419 The token's lifetime has ended");
            throw new TokenTimeoutException();
        }
        else {
            log.error("404 Page Not Found");
            throw new NotFoundException();
        }
    }

    public boolean checkingRelevanceToken(String token) {
        Date currentTime = new Date();
        return repository.findByToken(token).getUrlCreationTime().
                getTime() + 600000 > currentTime.getTime();
    }
}
