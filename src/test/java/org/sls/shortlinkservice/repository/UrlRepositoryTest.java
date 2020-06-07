package org.sls.shortlinkservice.repository;

import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.sls.shortlinkservice.service.UrlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
public class UrlRepositoryTest {
    @Autowired
    private UrlService service;

    @Autowired
    private UrlRepository repository;

    @Test
    void findByOriginalUrl() {
        Assert.assertEquals(service.createShortUrl("yandex.ru"), "localhost:8080/" + repository.findByOriginalUrl("yandex.ru").getToken());
        log.info(repository.findByOriginalUrl("yandex.ru").getOriginalUrl() + repository.findByOriginalUrl("yandex.ru").getToken());
    }
}
