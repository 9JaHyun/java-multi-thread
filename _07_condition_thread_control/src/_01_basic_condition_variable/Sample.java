package _01_basic_condition_variable;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Sample {

    Lock lock = new ReentrantLock();
    Condition condition = lock.newCondition();
    String username, password = null;

    // Thread가 할일1
    public void checkNull() {
        lock.lock();
        try {
            while (username == null || password == null) {
                condition.await(); // condition lock
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            lock.unlock();  // lock : 원자적으로 unlock (조건을 체크하는 역할은 condition이 담당)
        }
        checkIdAndPassword();
    }

    private void checkIdAndPassword() {
    }

    // Thread가 할일2
    private void getUsernamePasswordFromUI() {
        lock.lock();
        try {
            username = "아이디를 UI에서 가져오기";
            password = "비밀번호를 UI에서 가져오기";
            condition.signal(); // condition unlock
        } finally {
            lock.unlock();
        }
    }
}
