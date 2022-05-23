public class _01_Thread_Basic {

    public static void main(String[] args) throws InterruptedException {
//        basic();
//        expendThreadMethod();
        exceptionControl();
    }

    private static void basic() throws InterruptedException {
        Thread thread = new Thread(() -> {
            System.out.println("hello " + Thread.currentThread().getName());
        });

        System.out.println(Thread.currentThread().getName() + " before thread.start()");
        thread.start();
        System.out.println(Thread.currentThread().getName() + " after thread.start()");

        // 이 시간이 지날떄 까지 현재 스레드를 스케쥴링하지 말라고 운영체제에게 지시
        Thread.sleep(1000);
    }

    private static void expendThreadMethod() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("hello " + Thread.currentThread().getName());
                System.out.println("현재 스레드의 우선순위: " + Thread.currentThread().getPriority());
            }
        });

        // 유지보수를 위해서는 스레드의 이름을 붙이는 것도 좋은 방법
        thread.setName("새로운 스레드");
        thread.setPriority(Thread.MAX_PRIORITY);

        System.out.println(Thread.currentThread().getName() + " before thread.start()");
        thread.start();
        System.out.println(Thread.currentThread().getName() + " after thread.start()");
    }

    // 멀티스레드의 경우 스레드 하나라도 예외가 발생하게 되면 나머지들도 예외가 발생한다.
    // 미리 이 예외를 처리해야 함.  => 예외 핸들러 지정
    private static void exceptionControl() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("hello " + Thread.currentThread().getName());
                throw new RuntimeException("내부 오류");
            }
        });

        thread.setName("ErrorThread");

        // 스레드 내부에서 예외가 catch 되지 않는다면 핸들러에서 처리
        thread.setUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread t, Throwable e) {
                System.out.println("[" + t.getName() + "]" + "스레드에 심각한 오류가 발생했습니다."
                + " 에러내용 : " + e.getMessage());
            }
        });


        System.out.println(Thread.currentThread().getName() + " before thread.start()");
        thread.start();
        System.out.println(Thread.currentThread().getName() + " after thread.start()");
    }
}
