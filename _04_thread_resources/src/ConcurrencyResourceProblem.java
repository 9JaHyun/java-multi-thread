public class ConcurrencyResourceProblem {

    public static void main(String[] args) throws InterruptedException {
        Account account = new Account();
        DepositThread depositThread = new DepositThread(account);
        WitdrawThread withdrawThread = new WitdrawThread(account);

        depositThread.start();
        withdrawThread.start();

        depositThread.join();
        withdrawThread.join();

        System.out.println("현재 잔액은 " + account.getBalance() + "원 입니다.");
    }

    private static class DepositThread extends Thread {

        private Account account;

        public DepositThread(Account account) {
            this.account = account;
        }

        @Override
        public void run() {
            for (int i = 0; i < 10000; i++) {
                account.deposit(i);
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
            for (int i = 0; i < 10000; i++) {
                account.withdraw(i);
            }
        }
    }

    private static class Account {

        private long balance = 0;

        public void deposit(int amount) {
            balance += amount;
        }

        public void withdraw(int amount) {
            balance -= amount;
        }

        public long getBalance() {
            return balance;
        }
    }
}
