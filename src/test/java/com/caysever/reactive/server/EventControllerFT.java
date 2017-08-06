package com.caysever.reactive.server;

import com.caysever.reactive.Event;
import com.caysever.reactive.server.controller.EventController;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.http.MediaType.TEXT_EVENT_STREAM;

@SpringBootTest
@RunWith(SpringRunner.class)
public class EventControllerFT {

    @Autowired
    private WebApplicationContext wac;
    @Autowired
    EventController controller;

    private MockMvc mockMvc;
    private WebTestClient client;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
        client = WebTestClient.bindToController(controller).build();
    }

    @Test
    public void should_get_events_by_id() throws Exception {
        client.get()
                .uri("/events/42")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
                .expectBody(Event.class);

    }

    @Test
    public void should_get_events() throws Exception {
        client.get().uri("/events/")
                .accept(TEXT_EVENT_STREAM)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(TEXT_EVENT_STREAM)
                .expectBody(Event.class);

    }
}
