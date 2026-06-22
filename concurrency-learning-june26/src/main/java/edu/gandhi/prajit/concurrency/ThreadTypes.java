package edu.gandhi.prajit.concurrency;

import java.time.Duration;

public class ThreadTypes {

    public static void main(String[] args) {
        var userWorkerThread = new Thread(() -> {
            try {
                System.out.println("=====Running User Worker Thread=====");
                Thread.sleep(Duration.ofSeconds(10));
                System.out.println("=====Exited User Worker Thread=====");
            } catch (InterruptedException exception) {
                exception.printStackTrace();
            }
        });
        // daemon thread will run in background and it will not prevent the JVM from exiting when the program finishes its execution.
        var daemonThread = new Thread(() -> {
            while (true) {
                try {
                    System.out.println("=====Running Daemon Thread=====");
                    Thread.sleep(Duration.ofMillis(1000));
                } catch (InterruptedException exception) {
                    exception.printStackTrace();
                }
            }
        });
        userWorkerThread.setDaemon(false);
        daemonThread.setDaemon(true);

        userWorkerThread.start();
        daemonThread.start();
    }
}
