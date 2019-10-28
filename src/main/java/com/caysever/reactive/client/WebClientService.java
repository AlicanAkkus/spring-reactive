package com.caysever.reactive.client;

import com.caysever.reactive.model.Event;
import com.caysever.reactive.server.controller.EventRequest;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.time.Instant;
import java.time.ZonedDateTime;

@Service
public class WebClientService {

    @Bean
    WebClient webClient() {
        return WebClient.builder().baseUrl("http://localhost:8080").clientConnector(new ReactorClientHttpConnector()).build();
    }

    @Bean
    CommandLineRunner retrieve(WebClient client) {
        return args -> client.get().uri("/events?id=" + Instant.now().getEpochSecond())
                .accept(MediaType.TEXT_EVENT_STREAM)
                .exchange()
                .flux()
                .flatMap(cr -> cr.bodyToFlux(Event.class))
                .subscribe(System.out::println);
    }

    @Bean
    CommandLineRunner create(WebClient client) {
        return args -> Flux.interval(Duration.ofSeconds(1))
                .flatMap(p -> client.post().uri("/events")
                        .syncBody(new EventRequest(Instant.now().getEpochSecond(), ZonedDateTime.now().plusYears(1)))
                        .accept(MediaType.APPLICATION_JSON)
                        .exchange()
                        .flatMap(cr -> cr.bodyToMono(Event.class)))
                .subscribe(event -> System.out.println("Created event: " + event.toString()));
    }
}