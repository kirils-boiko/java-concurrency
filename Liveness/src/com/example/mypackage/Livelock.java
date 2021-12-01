
/* Since there are no livelock examples in Oracle's Tutorial on Java Concurrency,
    the following example is adapted from Tim Buchalka's Java Programming Masterclass
    covering Java 11 and Java 17;

    Livelock is a case when two thread are too busy responding to each other
    to make further progress;

 */
package com.example.mypackage;

public class Livelock {

    static class SharedResource {
        private Worker owner;

        public SharedResource(Worker owner) {
            this.owner = owner;
        }

        public Worker getOwner() {
            return owner;
        }

        public synchronized void setOwner(Worker owner) {
            this.owner = owner;
        }
    }

    static class Worker {
        private String name;
        private boolean active;

        public Worker(String name, boolean active) {
            this.name = name;
            this.active = active;
        }

        public String getName() {
            return name;
        }

        public boolean isActive() {
            return active;
        }

        public synchronized void work(SharedResource sharedResource,
                                      Worker otherWorker) {
            while (active) {
                if (sharedResource.getOwner() != this) {
                    try {
                        wait(3000);
                    } catch (InterruptedException e) {
                        System.err.println(Thread.currentThread().getName() +
                                ": interrupted");
                    }
                    continue;
                }

                if (otherWorker.isActive()) {
                    System.out.println(this.getName() + ": I cannot work such tough jobs, " +
                            otherWorker.getName() + " should work instead!");
                    sharedResource.setOwner(otherWorker);
                    continue;
                }

                System.out.println(getName() + ": working with the pickaxe");
                active = false;
                sharedResource.setOwner(otherWorker);
            }
        }
    }

    public static void main(String[] args) {
        final Worker alphonse = new Worker("Alphonse", true);
        final Worker gaston = new Worker("Gaston", true);
        final SharedResource pickaxe = new SharedResource(alphonse);

        new Thread(new Runnable() {
            @Override
            public void run() {
                gaston.work(pickaxe, alphonse);
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                alphonse.work(pickaxe, gaston);
            }
        }).start();
    }

}
