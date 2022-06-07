import java.util.Random;

public class Atomic_Volatile {

    public static void main(String[] args) {
        Metrics metrics = new Metrics();

        BusinessLogic businessLogic1 = new BusinessLogic(metrics);
        BusinessLogic businessLogic2 = new BusinessLogic(metrics);

        MetricsPrinter metricsPrinter = new MetricsPrinter(metrics);

        businessLogic1.start();
        businessLogic2.start();
        metricsPrinter.start();
    }


    public static class MetricsPrinter extends Thread {

        private Metrics metrics;

        public MetricsPrinter(Metrics metrics) {
            this.metrics = metrics;
        }

        @Override
        public void run() {
            while (true) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                }
                double currentAverage = metrics.getAverage();

                System.out.println("현재 평균값은... " + currentAverage);
            }
        }
    }

    public static class BusinessLogic extends Thread {

        private Metrics metrics;
        private Random random = new Random();

        public BusinessLogic(Metrics metrics) {
            this.metrics = metrics;
        }

        @Override
        public void run() {
            long start = System.currentTimeMillis();

            try {
                Thread.sleep(random.nextInt(50));
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            long end = System.currentTimeMillis();

            metrics.addSample(end - start);
        }
    }


    public static class Metrics {
        private long count = 0;
        private volatile double average = 0.0;

        // write 작업은 synchronized를 통해 원자성을 유지할 수 있다.
        public synchronized void addSample(long sample) {
            double currentSum = average * count;
            count++;
            average = (currentSum + sample) / count;
        }

        // 쿼리는 그자체로 원자성을 가지고 있으나, 대상이 되는 변수가 원자성 특징을 가져야 한다.
        // 이 경우 volatile 사용.
        public double getAverage() {
            return average;
        }
    }
}
