package com.app.homeworkoutapplication.common;

import java.io.Serializable;
import java.util.UUID;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public abstract class Event<T extends Serializable> extends ApplicationEvent {

    private final UUID uuid;

    private T eventData;

    private T oldEventData;

    public Event(Object source) {
        super(source);
        this.uuid = UUID.randomUUID();
    }

    public Event(Object source, T eventData) {
        super(source);
        this.uuid = UUID.randomUUID();
        this.eventData = eventData;
    }

    public Event(Object source, T oldEventData, T eventData) {
        super(source);
        this.uuid = UUID.randomUUID();
        this.oldEventData = oldEventData;
        this.eventData = eventData;
    }

    public abstract String getName();

    public abstract String getType();
}

