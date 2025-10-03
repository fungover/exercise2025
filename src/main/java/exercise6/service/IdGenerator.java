package exercise6.service;

import java.util.concurrent.atomic.AtomicInteger;

public class IdGenerator {

    private static final AtomicInteger nextId = new AtomicInteger(1);

    public static int getNextId() {
        return nextId.getAndIncrement();
    }

}
