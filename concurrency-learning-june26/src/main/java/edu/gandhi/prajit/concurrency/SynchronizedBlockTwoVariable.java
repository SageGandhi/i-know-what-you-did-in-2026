package edu.gandhi.prajit.concurrency;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SynchronizedBlockTwoVariable {
    private int firstCounter = 0;
    private int secondCounter = 0;

    private Object lockFirstCounter = new Object();
    private Object lockSecondCounter = new Object();

    public void incrementFirstCounter() {
        synchronized (lockFirstCounter) {
            firstCounter++;
        }
    }

    public void incrementSecondCounter() {
        synchronized (lockSecondCounter) {
            secondCounter++;
        }
    }

    public static void main(String[] args) {
        var synchronizedBlockTwoVariable = new SynchronizedBlockTwoVariable();
        var threadFirst = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                System.out.println("Incrementing First Counter: " + synchronizedBlockTwoVariable.firstCounter);
                synchronizedBlockTwoVariable.incrementFirstCounter();
            }
        });

        var threadSecond = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                System.out.println("Incrementing Second Counter: " + synchronizedBlockTwoVariable.secondCounter);
                synchronizedBlockTwoVariable.incrementSecondCounter();
            }
        });

        threadFirst.start();
        threadSecond.start();

        try {
            threadFirst.join();
            threadSecond.join();
            System.out.println("First Counter: " + synchronizedBlockTwoVariable.firstCounter);
            System.out.println("Second Counter: " + synchronizedBlockTwoVariable.secondCounter);
        } catch (InterruptedException exception) {
            log.error("Interrupted", exception);
        }
    }
}
