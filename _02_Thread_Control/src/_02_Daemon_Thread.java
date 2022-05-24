import java.math.BigInteger;

public class _02_Daemon_Thread {


    public static void main(String[] args) throws InterruptedException {
        _02_Daemon_Thread daemonThreadTest = new _02_Daemon_Thread();

        // 이전에는 종료되지 않았지만, 데몬 스레드로 설정되어있기 때문에 애플리케이션의 종료를 막지 않는다.
        // 이로 인해 main()이 종료됨에 따라 아직 계산이 끝마쳐지지 않았지만 종료됨.
        daemonThreadTest.test();
    }

    private void test() throws InterruptedException {
        Thread thread = new Thread(
              new LongComputationTask(new BigInteger("20000"), new BigInteger("100000000")));

        // 데몬 스레드 설정
        thread.setDaemon(true);
        thread.start();
        Thread.sleep(100);
        thread.interrupt();
    }
    private static class LongComputationTask implements Runnable {

        private BigInteger base;
        private BigInteger power;

        public LongComputationTask(BigInteger base, BigInteger power) {
            this.base = base;
            this.power = power;
        }

        @Override
        public void run() {
            System.out.println(base + "^" + power + " = " + pow(base, power));
        }

        private BigInteger pow(BigInteger base, BigInteger power) {
            BigInteger result = BigInteger.ONE;

            for (BigInteger i = BigInteger.ZERO; i.compareTo(power) != 0;
                  i = i.add(BigInteger.ONE)) {
                result = result.multiply(base);
            }
            return result;
        }
    }
}
