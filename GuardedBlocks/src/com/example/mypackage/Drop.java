package com.example.mypackage;

/* Producer-Consumer application using guarded blocks:
    Two threads use a shared object:
    Producer creates the data and does not deliver new data if Consumer hasn't
        retrieved the old data;
    Consumer retrieves the data and does not attempt it before the Producer
        thread has delivered it;

 */

public class Drop {

    private String message;
    private boolean empty = true;

    public synchronized String retrieve() {
        // wait until the message is available
        while (empty) {
            try {
                wait();
            } catch (InterruptedException e) {
                System.err.println(Thread.currentThread().getName() +
                        " was interrupted");
                return null;
            }
        }
        empty = true;
        notifyAll();    // notify the producer

        return message;
    }

    public synchronized void deliver(String message) {
        // wait until the message has been retrieved
        while (!empty) {
            try {
                wait();
            } catch (InterruptedException e) {
                System.err.println(Thread.currentThread().getName() +
                        " was interrupted");
                return;
            }
        }
        empty = false;
        this.message = message;     // store the message
        notifyAll();    // notify the producer
    }


}
