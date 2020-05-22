package org.sls.shortlinkservice.controller;

import org.junit.jupiter.api.Test;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.Matchers.startsWith;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class OriginalUrlControllerTest extends ConfigTestController {
    @Test
    @Transactional
    void shouldWorkWithValidUrl() throws Exception {
        String body = "{ \"originalUrl\" : \"https://yandex.ru\" }";
        mvc.perform(post("/create")
                .contentType(APPLICATION_JSON_VALUE)
                .content(body)
        )
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.link", startsWith("/"))
                );
    }

    @Test
    void shouldWorkWithoutProtocolUrl() throws Exception {
        String body = "{ \"originalUrl\" : \"google.com\" }";
        mvc.perform(post("/create")
                .contentType(APPLICATION_JSON_VALUE)
                .content(body)
        )
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.link", startsWith("/"))
                );
    }

    @Test
    void shouldWithNotAUrl() throws Exception {
        String body = "{ \"originalUrl\" : \"crypto message service\" }";
        mvc.perform(post("/create")
                .contentType(APPLICATION_JSON_VALUE)
                .content(body)
        )
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.link", startsWith("/"))
                );
    }

    @Test
    void shouldNotAcceptEmptyUrl() throws Exception {
        String body = "{ \"originalUrl\" : \"\" }";
        mvc.perform(post("/create")
                .contentType(APPLICATION_JSON_VALUE)
                .content(body)
        )
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldNotAcceptNullUrl() throws Exception {
        String body = "{ \"originalUrl\" : null }";
        mvc.perform(post("/create")
                .contentType(APPLICATION_JSON_VALUE)
                .content(body)
        )
                .andExpect(status().isBadRequest());
    }

}
