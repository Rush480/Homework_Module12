import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class FizzBuzz {
    private final int n;
    private volatile int currentNumber = 1;
    private final Queue<String> queue = new ConcurrentLinkedQueue<>();
    private final Object monitor = new Object();

    public FizzBuzz(int n) {
        this.n = n;
    }

    public static void main(String[] args) {
        FizzBuzz fizzBuzz = new FizzBuzz(15);
        fizzBuzz.start();
    }

    public void start() {
        Thread threadA = new Thread(this::fizz);
        Thread threadB = new Thread(this::buzz);
        Thread threadC = new Thread(this::fizzbuzz);
        Thread threadD = new Thread(this::number);

        threadA.start();
        threadB.start();
        threadC.start();
        threadD.start();

        try {
            threadA.join();
            threadB.join();
            threadC.join();
            threadD.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private void fizz() {
        try {
            while (true) {
                synchronized (monitor) {
                    if (currentNumber > n) {
                        monitor.notifyAll();
                        break;
                    }
                    if (currentNumber % 3 == 0 && currentNumber % 5 != 0) {
                        queue.add("fizz");
                        currentNumber++;
                        monitor.notifyAll();
                    } else {
                        monitor.wait();
                    }
                }
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private void buzz() {
        try {
            while (true) {
                synchronized (monitor) {
                    if (currentNumber > n) {
                        monitor.notifyAll();
                        break;
                    }
                    if (currentNumber % 5 == 0 && currentNumber % 3 != 0) {
                        queue.add("buzz");
                        currentNumber++;
                        monitor.notifyAll();
                    } else {
                        monitor.wait();
                    }
                }
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private void fizzbuzz() {
        try {
            while (true) {
                synchronized (monitor) {
                    if (currentNumber > n) {
                        monitor.notifyAll();
                        break;
                    }
                    if (currentNumber % 3 == 0 && currentNumber % 5 == 0) {
                        queue.add("fizzbuzz");
                        currentNumber++;
                        monitor.notifyAll();
                    } else {
                        monitor.wait();
                    }
                }
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private void number() {
        try {
            while (true) {
                synchronized (monitor) {
                    if (currentNumber > n && queue.isEmpty()) {
                        monitor.notifyAll();
                        break;
                    }
                    if (!queue.isEmpty()) {
                        System.out.println(queue.poll());
                    } else if (currentNumber % 3 != 0 && currentNumber % 5 != 0) {
                        System.out.println(currentNumber);
                        currentNumber++;
                        monitor.notifyAll();
                    } else {
                        monitor.wait();
                    }
                }
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
