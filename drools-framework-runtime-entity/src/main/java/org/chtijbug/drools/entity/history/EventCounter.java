package org.chtijbug.drools.entity.history;

import java.util.concurrent.atomic.AtomicLong;

public final class EventCounter {
    private static AtomicLong nextEventId = new AtomicLong();

    public static Long Next() {
        return nextEventId.getAndIncrement();
    }

}
