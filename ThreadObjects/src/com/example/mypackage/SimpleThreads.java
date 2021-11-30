package com.example.mypackage;

/* SimpleThreads example consists of two threads - the first is the main
    thread which creates new thread from Runnable object, MessageLoop, and
    waits for it to finish or interrupts it the thread takes too long;

 */

public class SimpleThreads {

    // Display a message preceded by the name of the current thread
    static void threadMessage(String message){
        String threadName = Thread.currentThread().getName();
        System.out.format("%s: %s%n", threadName, message);
    }

    private static class MessageLoop implements Runnable {
        @Override
        public void run() {
            String programmingLanguages[] = {
                    "Java",
                    "Python",
                    "C++",
                    "C",
                    "C#"
            };
            try {
                for (String lang : programmingLanguages) {
                    Thread.sleep(4000);
                    threadMessage(lang);
                }
            } catch (InterruptedException e) {
                threadMessage(" interrupted");
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        // Delay, in milliseconds before main interrupts MessageLoop thread
        long patience = 1000 * 60 * 60;     // one hour

        // if command line argument gives patience in seconds
        if (args.length > 0) {
            try {
                patience = Long.parseLong(args[0]) * 1000;
            } catch (NumberFormatException e) {
                System.err.println("Argument must be an integer.");
                System.exit(1);
            }
        }

        threadMessage("Starting MessageLoop thread");
        long startTime = System.currentTimeMillis();
        Thread t = new Thread(new MessageLoop());
        t.start();

        threadMessage("Waiting for MessageLoop thread to finish");
        while (t.isAlive()) {
            threadMessage("Still waiting...");
            t.join(1000);   // wait max 1 sec for MessageLoop to finish
            if (((System.currentTimeMillis() - startTime) > patience) &&
                t.isAlive()) {
                threadMessage("Patience time is out");
                t.interrupt();
                t.join();
            }
        }

        threadMessage("Finally!");
    }
}
