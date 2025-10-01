package org.example;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class Concurrency {
    static AtomicInteger counter = new AtomicInteger();

    static void main() throws InterruptedException {
//        List<String> v = new ArrayList<>();
//        List<String> v1 =  new CopyOnWriteArrayList<>();
//        var v2 = Collections.synchronizedCollection(v);
//        var v3 = Collections.unmodifiableList(v);

        Thread.ofVirtual().start(Concurrency::updateCounter);
        Thread.ofVirtual().start(Concurrency::updateCounter);
        Thread.ofVirtual().start(Concurrency::updateCounter);
        Thread.ofVirtual().start(Concurrency::updateCounter);
        Thread.ofVirtual().start(Concurrency::updateCounter);
        updateCounter();

        Thread.sleep(3000);
        System.out.println(counter);
    }
    static void updateCounter() {
        int i = 0;
        while(i < 1000000){
            counter.incrementAndGet();
            i++;
        }
    }
}
