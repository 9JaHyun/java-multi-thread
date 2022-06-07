package _02_wait_notify;

public class Test {
    private class SimpleCountDownLatch {
        private int count;

        private SimpleCountDownLatch(int count) {
            this.count = count;
            if (count < 0) {
                throw new IllegalArgumentException("count cannot be negative");
            }
        }

        private void await() throws InterruptedException {
            synchronized (this) {
                while (count > 0) {
                    this.wait();
                }
            }
        }

        private void countDown() {
            synchronized (this) {
                if (count > 0) {
                    count--;

                    if (count == 0) {
                        this.notifyAll();
                    }
                }
            }
        }

        private int getCount() {
            return this.count;
        }
    }
}
