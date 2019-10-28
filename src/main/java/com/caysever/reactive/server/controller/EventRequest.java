package com.caysever.reactive.server.controller;

import java.time.ZonedDateTime;

public class EventRequest {

    private Long id;
    private ZonedDateTime when;
    private String message;

    public EventRequest() {
    }

    public EventRequest(Long id, ZonedDateTime when) {
        this.id = id;
        this.when = when;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getWhen() {
        return when;
    }

    public void setWhen(ZonedDateTime when) {
        this.when = when;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}