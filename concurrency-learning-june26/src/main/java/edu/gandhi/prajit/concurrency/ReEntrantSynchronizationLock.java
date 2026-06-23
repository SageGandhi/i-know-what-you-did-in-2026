package edu.gandhi.prajit.concurrency;

public class ReEntrantSynchronizationLock {
    private final Object lock = new Object();

    private void methodA() {
        synchronized (lock) { // Acquires the lock
            System.out.println("Inside MethodA");
            methodB(); // Re-entrant call
        }
    }

    private void methodB() {
        synchronized (lock) { // Acquires the same lock again, allowed due to re-entrancy
            System.out.println("Inside MethodB");
        }
    }

    public static void main(String[] args) {
        new ReEntrantSynchronizationLock().methodA();
    }
}
