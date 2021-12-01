package com.example.mypackage;

/* One of the two ways of initiating a thread using the Thread object is by
    creating a class that inherits from Thread class and provide the
    implementation of the run() method (i.e., the code that will be executed once
    the Thread is initiated).
 */

public class HelloSubThread extends Thread {
    @Override
    public void run() {
        System.out.println("Hello from " + currentThread());
    }

    public static void main(String[] args) {
        new HelloSubThread().start();
    }
}
