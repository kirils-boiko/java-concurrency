package com.example.mypackage;

public class GuardedBlockExample {

    static class GuardedBlock {
        private boolean joy;

        public GuardedBlock(boolean joy) {
            this.joy = joy;
        }

        public synchronized void guardedJoy() {
            // This guard only loops once for each special event, which btw,
            //  may not be the event the thread is waiting for;
            // This is a more efficient guard since it invokes wait() to
            //  suspend the current thread and release the lock
            //  for the current object, and the invocation does not return
            //  until some special event is triggered by notify() or notifyAll()
            //  from other thread;
            // Note: since the special event may not be what the thread is waiting for
            //  always invoke wait inside a loop that tests for required condition;
            while (!joy) {
                try {
                    System.out.println("joy is false, suspending execution");
                    wait();
                    System.out.println("special event occurred, continuing execution");
                } catch(InterruptedException e) {
                    System.err.println(Thread.currentThread().getName() +
                            " was interrupted");
                    return;
                }
            }
            System.out.println("joy and efficiency have been achieved");
        }

        public synchronized void notifyJoy() {
            System.out.println("setting joy to true");
            joy = true;
            System.out.println("joy is set to true");
            notifyAll();
            System.out.println("all threads are notified");
        }
    }



    public static void main(String[] args) {


       GuardedBlock guardedBlock = new GuardedBlock(false);

        new Thread(new Runnable() {
            @Override
            public void run() {
                guardedBlock.guardedJoy();
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(4000);
                    guardedBlock.notifyJoy();
                } catch (InterruptedException e) {
                    System.err.println(Thread.currentThread().getName() +
                            " was interrupted");
                    return;
                }
            }
        }).start();
    }
}
