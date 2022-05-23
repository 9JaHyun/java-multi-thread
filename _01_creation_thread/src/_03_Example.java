import static java.lang.Thread.sleep;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class _03_Example {
    private static final int MAX_PASSWORD = 9999;

    public static void main(String[] args) {
        Random password = new Random();

        Vault vault = new Vault(password.nextInt(MAX_PASSWORD));

        List<Thread> threads = new ArrayList<>();

        threads.add(new AscendingHackerThread(vault));
        threads.add(new DescendingHackerThread(vault));
        threads.add(new PoliceThread());

        for (Thread thread : threads) {
            thread.start();
        }
    }

    private static class Vault {

        private int password;

        public Vault(int password) {
            this.password = password;
        }

        public boolean isCorrectPassword(int guess) {
            try {
                sleep(5);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return this.password == guess;
        }
    }

    private static abstract class HackerThread extends Thread {

        public Vault vault;

        public HackerThread(Vault vault) {
            this.vault = vault;
            this.setName(this.getClass().getSimpleName());
            this.setPriority(Thread.MAX_PRIORITY);
        }

        @Override
        public synchronized void start() {
            System.out.println("start thread: " + this.getName());
            super.start();
        }
    }

    private static class AscendingHackerThread extends HackerThread {
        
        public AscendingHackerThread(Vault vault) {
            super(vault);
        }
        
        @Override
        public void run() {
            for (int guess = 0; guess < MAX_PASSWORD; guess++) {
                if(vault.isCorrectPassword(guess)) {
                    System.out.println(this.getName() + " guess the password " + guess);
                    System.exit(0);
                }
            }
            super.run();
        }
    }
    private static class DescendingHackerThread extends HackerThread {

        public DescendingHackerThread(Vault vault) {
            super(vault);
        }

        @Override
        public void run() {
            for (int guess = MAX_PASSWORD; guess >= 0 ; guess--) {
                if(vault.isCorrectPassword(guess)) {
                    System.out.println(this.getName() + " guess the password " + guess);
                    System.exit(0);
                }
            }
            super.run();
        }
    }

    private static class PoliceThread extends Thread {

        @Override
        public void run() {
            for (int i = 10; i > 0; i--) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.println(i);
            }
            System.out.println("Gotcha!");
            System.exit(0);
        }
    }

}
