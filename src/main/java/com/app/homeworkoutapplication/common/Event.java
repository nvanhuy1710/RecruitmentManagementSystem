package com.app.homeworkoutapplication.common;

import java.io.Serializable;
import java.util.UUID;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public abstract class Event<T extends Serializable> extends ApplicationEvent {

    private T eventData;

    public Event(Object source) {
        super(source);
    }

    public Event(Object source, T eventData) {
        super(source);
        this.eventData = eventData;
    }

    public abstract String getName();

    public abstract String getType();
}

