package com.example.mypackage;

/* Illustrating the use of Lock objects from java.util.concurrent package
    to solve the problem of a deadlock presented in Liveness project;
    Biggest advantage of Lock object over implicit locks is the ability
    to back out of an attempt to acquire a lock through tryLock() and
    lockInterruptibly() methods;
 */

import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class SafeLock {

    static class Friend {
        private final String name;
        private final Lock lock = new ReentrantLock();

        public Friend(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public boolean impendingBow(Friend bower) {
            Boolean myLock = false;
            Boolean yourLock = false;
            try {
                myLock = lock.tryLock();
                yourLock = bower.lock.tryLock();
            } finally {
                if (!(myLock && yourLock)) {
                    // if one of the locks is not acquired, release the other lock
                    if (myLock) {
                        lock.unlock();
                    }
                    if (yourLock) {
                        bower.lock.unlock();
                    }
                }
            }
            return myLock && yourLock;
        }

        public void bow(Friend bower) {
            if (impendingBow(bower)) {
                try {
                    System.out.printf("%s: %s has bowed to me!%n",
                            this.name, bower.getName());
                } finally {
                    // release both locks
                    lock.unlock();
                    bower.lock.unlock();
                }
            } else {
                System.out.printf("%s: %s started to bow to me, " +
                        "but saw that I was already bowing to him.%n",
                        this.name, bower.getName());
            }
        }
    }

    static class BowLoop implements Runnable {
        private Friend bower;
        private Friend bowee;

        public BowLoop(Friend bower, Friend bowee) {
            this.bower = bower;
            this.bowee = bowee;
        }

        @Override
        public void run() {
            Random random = new Random();
            for (;;) {
                try {
                    Thread.sleep(random.nextInt(4000));
                } catch (InterruptedException e) {
                    System.err.println(Thread.currentThread().getName() +
                            " was interrupted");
                }
                bowee.bow(bower);
            }
        }
    }

    public static void main(String[] args) {
        final Friend alphonse = new Friend("Alphonse");
        final Friend gaston = new Friend("Gaston");
        new Thread(new BowLoop(alphonse, gaston)).start();
        new Thread(new BowLoop(gaston, alphonse)).start();
    }
}
