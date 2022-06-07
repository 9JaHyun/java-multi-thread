public class DataRaceExample {

    public static void main(String[] args) {
        TestClass testClass = new TestClass();
        Thread incrementThread = new Thread(() -> {
            for (int i = 0; i < Integer.MAX_VALUE; i++) {
                testClass.increaseXY();
            }
        });

        Thread checkThread = new Thread(() -> {
            for (int i = 0; i < Integer.MAX_VALUE; i++) {
                testClass.checkDataRace();
            }
        });

        incrementThread.start();
        checkThread.start();

    }

    private static class TestClass {
        int x = 0;
        int y = 0;

        public void increaseXY() {
            x++;
            y++;
        }

        public void checkDataRace() {
            if (y > x) {
                throw new DataRaceException("데이터 경쟁 발생! - y++가 먼저 수행되었습니다.");
            }
            System.out.println("정상 - x++가 먼저 수행되었습니다.");
        }
    }

    private static class DataRaceException extends RuntimeException {

        public DataRaceException(String message) {
            super(message);
        }
    }
}
