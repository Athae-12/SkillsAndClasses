package com.athae.skillsandclasses.events;

import java.util.function.Consumer;

public abstract class EventConsumer<T extends skillandclassesEvent> implements Consumer<T> {

    // Less = call first, More = call later.
    public int callOrder() {
        return 0;
    }
}