package com.caysever.reactive;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.ZonedDateTime;

@Data
@Getter
@Setter
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

    @Override
    public String toString() {
        return "Event{" +
                "id=" + id +
                ", message='" + message + '\'' +
                ", when=" + when +
                '}';
    }
}
