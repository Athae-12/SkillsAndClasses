package com.athae.skillsandclasses.events;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class EventCaller<T extends skillandclassesEvent> {

    List<EventConsumer<T>> events = new ArrayList<>();
    Lock lock = new ReentrantLock();

    public T callEvents(T event) {
        events.forEach(x -> {
            if (!event.canceled) {
                try {
                    x.accept(event);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        return event;
    }

    public void register(EventConsumer<T> t) {
        lock.lock();
        try {
            this.events.add(t);
            events.sort(Comparator.comparingInt(EventConsumer::callOrder));
        } finally {
            lock.unlock();
        }
    }
}