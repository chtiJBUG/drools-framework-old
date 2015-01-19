package org.chtijbug.drools.entity.history;

import java.util.concurrent.atomic.AtomicLong;

public final class EventCounter {
    private AtomicLong nextEventId = new AtomicLong();

    private EventCounter(){}

    public Long next() {
        return nextEventId.getAndIncrement();
    }

    public Long current() {
        return nextEventId.get();
    }

    public static EventCounter newCounter() {
        return new EventCounter();
    }

}
