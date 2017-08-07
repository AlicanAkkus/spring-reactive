package com.caysever.reactive.client;

import com.caysever.reactive.Event;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class WebClientService {

    @Bean
    WebClient webClient() {
        return WebClient.builder().baseUrl("http://localhost:8080").clientConnector(new ReactorClientHttpConnector()).build();
    }

    @Bean
    CommandLineRunner runner(WebClient client) {
        return args -> {
            client.get().uri("/events")
                    .accept(MediaType.TEXT_EVENT_STREAM)
                    .exchange()
                    .flux()
                    .flatMap(cr -> cr.bodyToFlux(Event.class))
                    .subscribe(System.out::println);
        };
    }

}
