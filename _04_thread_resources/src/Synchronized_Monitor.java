import java.time.LocalTime;

public class Synchronized_Monitor {

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
                printLog("입금 전 잔액 : " + account.getBalance());
                account.deposit(100);
                printLog("입금 후 잔액 : " + account.getBalance());
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
                printLog("출금 전 잔액 : " + account.getBalance());
                account.withdraw(100);
                printLog("출금 후 잔액 : " + account.getBalance());
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }


    // 메서드 시그니처에 synchronized 키워드를 선언해버리는 방식
    // synchronized 키워드가 선언된 모든 메서드들을 묶어 단일 스레드 접근
    private static class Account {

        private long balance = 0;

        public synchronized void deposit(int amount) throws InterruptedException {
            printLog("=== Account.deposit() start ===");
            Thread.sleep(2000);
            balance += amount;
            printLog("=== Account.deposit() end ===");
        }

        public synchronized void withdraw(int amount) throws InterruptedException {
            printLog("=== Account.withdraw() start ===");
            Thread.sleep(2000);
            balance -= amount;
            printLog("=== Account.withdraw() end ===");
        }

        public synchronized long getBalance() {
            return balance;
        }
    }

    private static void printLog(String content) {
        System.out.println("[" + LocalTime.now() + "]     " + content);
    }
}