package section05_synchronizationConcept.exam01;

public class MultiThreadExample {

    private static int sum = 0;
    private static final Object lock = new Object();

    public static void main(String[] args) {

        long start = System.currentTimeMillis();

        Thread thread1 = new Thread(() ->{
            for (int i = 1; i <= 500; i++) {
                synchronized (lock){
                    sum += i;
                }
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        Thread thread2 = new Thread(() -> {
            for (int i = 501; i < 1001; i++) {
                synchronized (lock){
                    sum += i;
                }
                try{
                    Thread.sleep(1);
                //    throw new RuntimeException("error");
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        thread1.start();
        thread2.start();

        try{
            thread1.join();
            thread2.join();
        } catch (InterruptedException e){
            e.printStackTrace();
        }

        System.out.println("합계: " + sum);
        System.out.println("멀티 스레드 처리 시간: " + (System.currentTimeMillis() - start) + "ms");

        System.out.println("main 스레드를 종료합니다.");
    }
}
