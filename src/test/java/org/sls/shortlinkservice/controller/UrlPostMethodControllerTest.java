package org.sls.shortlinkservice.controller;

import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;
import static org.hamcrest.Matchers.startsWith;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class UrlPostMethodControllerTest extends ConfigTestController {
    @Test
    @Transactional
    void shouldWorkWithValidUrl() throws Exception {
        String body = "{ \"originalUrl\" : \" https://yandex.ru \" }";
        mvc.perform(post("/create")
                .contentType(APPLICATION_JSON_VALUE)
                .content(body)
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.link", startsWith(""))
                );
        mvc.perform(post("/create")
                .contentType(APPLICATION_JSON_VALUE)
                .content(body)
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.link", startsWith(""))
                );
        mvc.perform(get("/qedqK"))
                .andDo(print())
                .andExpect(status().is3xxRedirection());
        mvc.perform(MockMvcRequestBuilders.get("/qedqKq"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldWorkWithoutProtocolUrl() throws Exception {
        String body = "{ \"originalUrl\" : \"google.com\" }";
        mvc.perform(post("/create")
                .contentType(APPLICATION_JSON_VALUE)
                .content(body)
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.link", startsWith("localhost:8080/"))
                );
    }

    @Test
    void shouldWithNotAUrl() throws Exception {
        String body = "{ \"originalUrl\" : \"crypto message service\" }";
        mvc.perform(post("/create")
                .contentType(APPLICATION_JSON_VALUE)
                .content(body)
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.link", startsWith("localhost:8080/"))
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
