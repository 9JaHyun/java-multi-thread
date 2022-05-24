import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class _03_Join {

    public static void main(String[] args) throws InterruptedException {
        List<Long> inputList = List.of(501111L, 100L, 5324L, 1232L, 8090L, 5839L, 21L, 0L);

        List<FactorialThread> threads = new ArrayList<>();

        for (Long inputNum : inputList) {
            threads.add(new FactorialThread(inputNum));
        }

        for (FactorialThread thread : threads) {
            thread.start();
        }

        for (FactorialThread thread : threads) {
            thread.join(2000);
        }

        for (int i = 0; i < inputList.size(); i++) {
            if (threads.get(i).isFinished()) {
                System.out.println(inputList.get(i) + "! result is : " + threads.get(i).getResult());
            } else {
                System.out.println(inputList.get(i) + "! is still calculating...");
            }
        }
    }

    private static class FactorialThread extends Thread {

        private long inputNum;
        private BigInteger result = BigInteger.ZERO;
        private boolean isFinished = false;

        public FactorialThread(long inputNum) {
            this.inputNum = inputNum;
        }

        @Override
        public void run() {
            this.result = factorial(inputNum);
            this.isFinished = true;
        }

        private BigInteger factorial(long inputNum) {
            BigInteger result = BigInteger.ONE;

            for (long i = inputNum; i > 0 ; i--) {
                result = result.multiply(new BigInteger(Long.toString(i)));
            }
            return result;
        }

        public BigInteger getResult() {
            return result;
        }

        public boolean isFinished() {
            return isFinished;
        }
    }
}
