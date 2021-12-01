package com.example.mypackage;

/* Refer to Drop class for description
   Thread that retrieves the messages and prints them out until
   it receives "DONE" string, also pauses for random intervals;
 */

import java.util.Random;

public class Consumer implements Runnable {

    private Drop drop;

    public Consumer(Drop drop) {
        this.drop = drop;
    }

    @Override
    public void run() {
        Random random = new Random();
        for (String message = drop.retrieve(); !message.equals("DONE");
        message = drop.retrieve()) {
            System.out.println(Thread.currentThread().getName() +
                    ": retrieved "+ message);
            try {
                Thread.sleep(random.nextInt(5000));
            } catch (InterruptedException e) {
                System.err.println(Thread.currentThread().getName() +
                        " was interrupted");
            }
        }

        System.out.println(Thread.currentThread().getName() +
                ": exiting");
    }
}
