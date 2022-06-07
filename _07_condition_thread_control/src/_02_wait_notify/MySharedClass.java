package _02_wait_notify;

// 공유 객체를 하나 만들자.
public class MySharedClass {

    private boolean isComplete = false;

    public void waitUntilComplete() throws InterruptedException {
        synchronized (this) {
            while (!isComplete) {
                this.wait();
            }
        }
    }

    public void complete() {
        synchronized (this) {
            isComplete = true;
            this.notify();
        }
    }
}
