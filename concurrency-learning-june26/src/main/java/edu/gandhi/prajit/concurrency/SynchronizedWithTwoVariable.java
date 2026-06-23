package edu.gandhi.prajit.concurrency;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SynchronizedWithTwoVariable {
    private static int firstCounter = 0;
    private static int secondCounter = 0;

    public static synchronized void incrementFirstCounter() {
        firstCounter++;
    }
    // incrementFirstCounter and incrementSecondCounter are synchronized methods, but they are synchronized on the same lock (the class object), so they will block each other even though they are operating on different variables. This can lead to unnecessary contention and reduced performance.
    public static synchronized void incrementSecondCounter() {
        secondCounter++;
    }

    public static void main(String[] args) {
        var threadFirst = new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                System.out.println("Incrementing First Counter: ");
                incrementFirstCounter();
            }
        });

        var threadSecond = new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                System.out.println("Incrementing Second Counter: ");
                incrementSecondCounter();
            }
        });

        threadFirst.start();
        threadSecond.start();

        try {
            threadFirst.join();
            threadSecond.join();
            System.out.println("First Counter: " + firstCounter);
            System.out.println("Second Counter: " + secondCounter);
        } catch (InterruptedException exception) {
            log.error("Interrupted", exception);
        }
    }
}
