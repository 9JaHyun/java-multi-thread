import java.time.LocalTime;

public class Synchronized_Locking {

    public static void main(String[] args) throws InterruptedException {
        Account account = new Account();
        DepositThread depositThread = new DepositThread(account);
        WitdrawThread withdrawThread = new WitdrawThread(account);

        depositThread.start();
        withdrawThread.start();

        depositThread.join();
        withdrawThread.join();

        printLog("account의 현재 잔액은 " + account.getBalance() + "원 입니다.");
    }

    private static class DepositThread extends Thread {

        private Account account;

        public DepositThread(Account account) {
            this.account = account;
        }

        @Override
        public void run() {
            try {
                account.deposit(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private static class WitdrawThread extends Thread {

        private Account account;

        public WitdrawThread(Account account) {
            this.account = account;
        }

        @Override
        public void run() {
            try {
                account.withdraw(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }



    private static class Account {

        private long balance = 0;
        Object lock = new Object();

        public void deposit(int amount) throws InterruptedException {
            printLog("=== Account.deposit() start ===");
            synchronized (this.lock) {
                System.out.println("--- deposit() 처리중... ---");
                Thread.sleep(2000);
                balance += amount;
            }
            printLog("=== Account.deposit() end ===");
        }

        public void withdraw(int amount) throws InterruptedException {
            printLog("=== Account.withdraw() start ===");
            synchronized (this.lock) {
                System.out.println("--- withdraw() 처리중... ---");
                Thread.sleep(2000);
                balance -= amount;
            }
            printLog("=== Account.withdraw() end ===");
        }

        public long getBalance() {
            synchronized (this.lock) {
                return balance;
            }
        }
    }

    private static void printLog(String content) {
        System.out.println("[" + LocalTime.now() + "]     " + content);
    }
}