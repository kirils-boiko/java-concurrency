package com.example.mypackage;


/*
    Thread.sleep() method causes the current thread to suspend execution for
    a specified period - efficient means of making processor available to other
    threads
 */

public class SleepInterruptJoin {

    public static void main(String[] args) {
        try {
            for (int i = 0; i < 10; i++) {
                Thread.sleep(1000); // pause for 1 sec
                System.out.println(i);
            }

            if (!Thread.interrupted()) {
                Thread.currentThread().interrupt();
            }

            if (Thread.interrupted()) {
                throw new InterruptedException("manual interruption");
            }

        } catch (InterruptedException e) {
            System.out.println(Thread.currentThread() + " was interrupted: " +
                    e.getMessage());
            return;
        }
    }
}
