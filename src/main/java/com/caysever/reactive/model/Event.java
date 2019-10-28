package com.caysever.reactive.model;

import java.time.ZonedDateTime;

public class Event {

    private Long id;
    private String message;
    private ZonedDateTime when;

    public Event() {
    }

    public Event(Long id, ZonedDateTime when) {
        this(id, "**" + id + "**", when);
    }

    public Event(Long id, String message, ZonedDateTime when) {
        this.id = id;
        this.message = message;
        this.when = when;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ZonedDateTime getWhen() {
        return when;
    }

    public void setWhen(ZonedDateTime when) {
        this.when = when;
    }

    @Override
    public String toString() {
        return "Event{" +
                "id=" + id +
                ", message='" + message + '\'' +
                ", when=" + when +
                '}';
    }
}
