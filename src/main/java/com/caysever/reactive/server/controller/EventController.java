package com.caysever.reactive.server.controller;

import com.caysever.reactive.Event;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

import java.time.Duration;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.stream.Stream;

@RestController
public class EventController {

    @GetMapping("/events/{id}")
    public Mono<Event> eventById(@PathVariable Long id) {
        return Mono.just(new Event(id, "**" + id + "**", ZonedDateTime.now()));
    }

    @GetMapping(value = "/events", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Event> events() {
        Flux<Event> eventFlux = Flux.fromStream(Stream.generate(() -> new Event(Instant.now().getEpochSecond(), ZonedDateTime.now())));
        Flux<Long> durationFlux = Flux.interval(Duration.ofSeconds(3));

        return Flux.zip(eventFlux, durationFlux).map(Tuple2::getT1);
    }

}
