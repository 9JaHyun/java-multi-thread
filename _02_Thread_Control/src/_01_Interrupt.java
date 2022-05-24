import java.math.BigInteger;

public class _01_Interrupt {

    public static void main(String[] args) {
        _01_Interrupt whenInterrupt = new _01_Interrupt();

//        whenInterrupt.test1();
//        whenInterrupt.test2();
        whenInterrupt.test3();
    }

    private void test1() {
        Thread thread = new Thread(new BlockingTask());
        thread.start();
        thread.interrupt();
    }

    private void test2() {
        Thread thread = new Thread(
              new LongComputationTask(new BigInteger("2"), new BigInteger("100000000")));
        thread.start();
        thread.interrupt();
    }

    private void test3() {
        // 아주 큰 값을 넣으면 시간이 오래 걸린다. => 인터럽트 시키거나 계속 진행하거나 선택해야 함.
        Thread thread = new Thread(
              new LongComputationTask(new BigInteger("2"), new BigInteger("100000000")));
        thread.start();

        // 사실 interrupt()만 썼다고 해서 해결이 안된다. 이를 처리할 Runnable 구현 코드 내에 로직이 따로 필요하다.
        thread.interrupt();
    }

    private static class BlockingTask implements Runnable {

        @Override
        public void run() {
            try {
                Thread.sleep(50000);
            } catch (InterruptedException e) {
                System.out.println("Exit blocking thread");
            }
        }
    }

    private static class LongComputationTask implements Runnable {

        private BigInteger base;
        private BigInteger power;

        public LongComputationTask(BigInteger base, BigInteger power) {
            this.base = base;
            this.power = power;
        }

        // pow 로직에서 interrupt() 수정 메서드를 추가
        @Override
        public void run() {
                System.out.println(base + "^" + power + " = " + powV2(base, power));
        }

        private BigInteger pow(BigInteger base, BigInteger power) {
            BigInteger result = BigInteger.ONE;

            for (BigInteger i = BigInteger.ZERO; i.compareTo(power) != 0;
                  i = i.add(BigInteger.ONE)) {
                result = result.multiply(base);
            }
            return result;
        }

        private BigInteger powV2(BigInteger base, BigInteger power) {
            BigInteger result = BigInteger.ONE;

            for (BigInteger i = BigInteger.ZERO; i.compareTo(power) != 0;
                  i = i.add(BigInteger.ONE)) {
                // Interrupt() 호출 시 이를 처리할 메서드 추가.
                if (Thread.currentThread().isInterrupted()) {
                    System.out.println("Interrupted!!");
                    return BigInteger.ZERO;
                }
                result = result.multiply(base);
            }
            return result;
        }
    }

}
