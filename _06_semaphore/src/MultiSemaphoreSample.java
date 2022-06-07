import java.util.ArrayDeque;
import java.util.Queue;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MultiSemaphoreSample {
    Queue<Item> itemQueue = new ArrayDeque<>();

    Semaphore full = new Semaphore(0);
    Semaphore empty = new Semaphore(10);  // Queue의 용량만큼 제공

    Lock lock = new ReentrantLock();    // 큐에 여러 스레드가 접근하는 것을 막기위한 락



    public void producer() throws InterruptedException {
        Item item = new Item("아이템");
        empty.acquire();
        lock.lock();
        itemQueue.offer(item);
        lock.unlock();
        full.release();
    }

    public void consumer() throws InterruptedException {
        full.acquire();
        lock.lock();
        itemQueue.poll();
        lock.unlock();
        empty.release();
    }
}
