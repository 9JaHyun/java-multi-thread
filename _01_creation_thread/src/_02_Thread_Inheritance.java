public class _02_Thread_Inheritance {

    public static void main(String[] args) {
        _02_Thread_Inheritance main = new _02_Thread_Inheritance();
        main.inheritanceThread();
    }

    // 기장 일반적으로 스레드를 생성하는 방법. -> 그때그때 Runnable 구현
    private void usingInnerClass() {
        Thread thread = new Thread(() -> {
            System.out.println("그때그때 Runnable 구현");
        });

        thread.start();
    }

    // Runnable의 또 다른 객체를 만드는 대신 스레드를 확장하는 새 클래스를 만들자.
    private void inheritanceThread() {
        Thread thread = new Thread(() -> {
            System.out.println("그때그때 Runnable 구현");
        });

        // 매우 편하다.
        NewThread thread2 = new NewThread();

        thread.start();
        thread2.start();
    }

    private static class NewThread extends Thread {
        // 스레드 로직 구현
        @Override
        public void run() {
//            System.out.println("Hello new Thread!" + Thread.currentThread().getName());
            // 정적 메서드를 호출하지 않아도 된다.
            System.out.println("Hello new Thread!" + this.getName());
        }
    }


    private void study3() {

    }
}
