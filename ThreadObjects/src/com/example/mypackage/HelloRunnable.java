package com.example.mypackage;

/* One of the two ways of initiating a thread using the Thread object is by
    Creating a class that implements a Runnable interface that defines
    a single method, run(), meant to contain the code executed in the thread.

    The Runnable object (e.g., an instance of HelloRunnable class) is then
    passed to the Thread constructor to initiate the Thread.

    The idiom that employs a Runnable object is preferred since it is more flexible
    and is applicable to the high-level thread management APIs.
 */

public class HelloRunnable implements Runnable {

    @Override
    public void run() {
        System.out.println("Hello from " + Thread.currentThread());
    }

    public static void main(String[] args) {
        new Thread(new HelloRunnable()).start();
    }
}
