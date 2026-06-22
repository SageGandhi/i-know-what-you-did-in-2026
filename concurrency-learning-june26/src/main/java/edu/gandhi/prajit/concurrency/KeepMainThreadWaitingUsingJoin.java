package edu.gandhi.prajit.concurrency;

import java.text.MessageFormat;

import lombok.SneakyThrows;

public class KeepMainThreadWaitingUsingJoin {
    @SneakyThrows
    public static void main(String[] args) {
        System.out.println("learning to run thread concurrently==>main=>1");

        var threadFirst = new Thread(() -> {
            System.out.println("learning to run thread concurrently==>1");
            try {
                Thread.sleep(5000);
                System.out.println("just finished sleeping thread==>1");
            } catch (InterruptedException exception) {
                System.err.println("Exception Occurred While Sleeping Thread==>1" + exception);
            }

        });

        var threadSecond = new Thread(() -> {
            System.out.println("learning to run thread concurrently==>2");
            try {
                Thread.sleep(5000);
                System.out.println("just finished sleeping thread==>2");
            } catch (InterruptedException exception) {
                System.err.println("Exception Occurred While Sleeping Thread==>2" + exception);
            }

        });
        threadFirst.start();
        threadSecond.start();
        
        //getting all running thread in current java virtual machine
        Thread.getAllStackTraces().keySet()
            .stream().map(thread->MessageFormat.format(
                "Thread Name: {0}, State: {1}", 
                thread.getName(), thread.getState()))
            .forEach(System.out::println);

        threadFirst.join(); //main thread waits for this thread to terminate.
        threadSecond.join();//main thread waits for this thread to terminate.

        System.out.println("learning to run thread concurrently==>main=>2");
    }
}
