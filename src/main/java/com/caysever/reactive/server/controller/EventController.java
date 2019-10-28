package com.caysever.reactive.server.controller;

import com.caysever.reactive.model.Event;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
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
    public Mono<Event> retrieve(@PathVariable Long id) {
        return Mono.just(new Event(id, "**" + id + "**", ZonedDateTime.now()));
    }

    @GetMapping(value = "/events", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Event> retrieve() {
        Flux<Event> eventFlux = Flux.fromStream(Stream.generate(() -> new Event(Instant.now().getEpochSecond(), ZonedDateTime.now()))).log();
        Flux<Long> durationFlux = Flux.interval(Duration.ofSeconds(3)).log();

        return Flux.zip(eventFlux, durationFlux).log().map(Tuple2::getT1).log();
    }

    @PostMapping("/events")
    public Mono<Event> create(@RequestBody Mono<EventRequest> eventRequest) {
        return eventRequest.map(event -> new Event(event.getId(), event.getWhen()));
    }
}