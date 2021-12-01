package com.example.mypackage;

/* Refer to Drop class for description
    Defines a thread that sends a series of messages
    String "DONE" indicates that all messages have been sent
    The producer pauses for random intervals between messages
 */

import java.util.Random;

public class Producer implements Runnable {
    private Drop drop;

    public Producer(Drop drop) {
        this.drop = drop;
    }

    @Override
    public void run() {
        String messages[] = {
                "01001000101001",
                "Got ya",
                "Hello",
                "Long live the king"
        };
        Random random = new Random();

        for (String message : messages) {
            System.out.println(Thread.currentThread().getName() +
                    ": delivering " + message);
            drop.deliver(message);
            try {
                Thread.sleep(random.nextInt(5000));
            } catch (InterruptedException e) {
                System.err.println(Thread.currentThread().getName() +
                        " was interrupted");
            }
        }

        drop.deliver("DONE");
        System.out.println(Thread.currentThread().getName() + ": exiting");
    }
}
