import java.util.concurrent.Semaphore;

public class SingleSemaphoreSample {
    // 세마포어를 첫번째로 얻는 스레드가 블록
    Semaphore full = new Semaphore(0);
    Semaphore empty = new Semaphore(1);

    Item item = null;

    public void producer() throws InterruptedException {
        empty.acquire();
        // 데이터를 계속 밀어넣음
        full.release();
    }

    public void consumer() throws InterruptedException {
        full.acquire();
        // 전달받은 데이터를 처리
        empty.release();
    }
}
