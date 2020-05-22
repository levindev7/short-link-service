package org.sls.shortlinkservice.service;

import org.hashids.Hashids;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sls.shortlinkservice.repository.UrlRepository;
import org.sls.shortlinkservice.exception.NotFoundException;
import org.sls.shortlinkservice.exception.TokenTimeoutException;
import org.sls.shortlinkservice.model.Url;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class UrlService  {
    private static final Logger logger = LoggerFactory.getLogger(
            UrlService.class);

    private final UrlRepository repository;

    public UrlService(UrlRepository repository) {
        this.repository = repository;
    }

    public String createShortUrl(String originalUrl) {
        Url url = new Url(originalUrl);

            Hashids hashids = new Hashids(originalUrl, 5);
            String hash = hashids.encode(url.getId());
            url.setToken(hash);

            url.setUrlCreationTime(new Date());

            repository.save(url);

            return url.getToken();

    }

    public String returnOriginalUrl(String token)  {
        if(token.equals(repository.findByToken(token).getToken())) {
            return repository.findByToken(token).getOriginalUrl();
        } else {
            logger.error("404 Page Not Found");
            throw new NotFoundException();
        }
    }

    public String returnOriginalUrlForRedirect(String token) {
        if(token.equals(repository.findByToken(token).getToken())
                && checkingRelevanceToken(token)) {
            return repository.findByToken(token).getOriginalUrl();
        }
        else if (token.equals(repository.findByToken(token).getToken())
                && checkingRelevanceToken(token) == false) {
            logger.error("419 The token's lifetime has ended");
            throw new TokenTimeoutException();
        }
        else {
            logger.error("404 Page Not Found");
            throw new NotFoundException();
        }

    }


    public boolean checkingRelevanceToken(String token) {
        Date currentTime = new Date();
        return repository.findByToken(token).getUrlCreationTime().
                getTime() + 600000 > currentTime.getTime();
    }
}
