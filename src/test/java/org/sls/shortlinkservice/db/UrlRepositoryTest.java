package org.sls.shortlinkservice.db;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.sls.shortlinkservice.model.Url;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;

@DataJpaTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class UrlRepositoryTest {

    @Autowired
    private UrlRepository repository;

    @Test
    void findByOriginalUrl() {
        Url url = new Url("somethingUrl");
        repository.save(url);
        Assert.assertSame(url.getOriginalUrl(), repository.findByOriginalUrl(url.getOriginalUrl()).getOriginalUrl());
    }
}
