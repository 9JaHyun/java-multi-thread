public class AtomicExercise {
    public static class MinMaxMetrics {
        private volatile long minValue = Long.MAX_VALUE;
        private volatile long maxValue = Long.MIN_VALUE;

        public synchronized void addSample(long newSample) {
            if (newSample < minValue) {
                minValue = newSample;
                return;
            }

            if (newSample > maxValue) {
                maxValue = newSample;
                return;
            }
        }

        public long getMin() {
            return minValue;
        }

        public long getMax() {
            return maxValue;
        }
    }
}
