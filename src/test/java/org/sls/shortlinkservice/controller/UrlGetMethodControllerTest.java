package org.sls.shortlinkservice.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.sls.shortlinkservice.repository.UrlRepository;
import org.sls.shortlinkservice.service.UrlService;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.Matchers.startsWith;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@SpringBootTest
public class UrlGetMethodControllerTest extends ConfigTestController {
    @Mock
    private UrlRepository repository;

    @InjectMocks
    private UrlService service;

    @Test
    public void returnOriginalUrlWithValidUrl() throws Exception {
        String x = service.createShortUrl("yandex.ru");

        this.mvc.perform(get("/getOriginalUrl" + x))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.originalUrl", startsWith(""))
                );
    }

/*    @Test
    public void nullTst() {
        Optional<Url> optionalUrl = Optional.ofNullable(

        )


        Hashids hashids = new Hashids(originalUrl, 5);
        String hash = hashids.encode(url.getId());
        url.setToken(hash);

        url.setUrlCreationTime(new Date());

        repository.save(url);

        return "/" + url.getToken();
    }*/
}
