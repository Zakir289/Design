/**
 * Created by zakirelahi on 06/08/15.
 */
public class ProConsPrb {
    public static void main(String[] args) {
        Godown godown = new Godown();
        Producer p1 = new Producer(godown, 1);
        Consumer c1 = new Consumer(godown, 1);
        p1.start();
        c1.start();
    }
}
class Godown {
    private int contents;
    private boolean available = false;
    public synchronized int get() {
        while (available == false) {
            try {
                wait();
            }
            catch (InterruptedException e) {
            }
        }
        available = false;
        notifyAll();
        return contents;
    }
    public synchronized void put(int value) {
        while (available == true) {
            try {
                wait();
            }
            catch (InterruptedException e) {
            }
        }
        contents = value;
        available = true;
        notifyAll();
    }
}

class Consumer extends Thread {
    private Godown g;
    private int number;
    public Consumer(Godown c, int number) {
        g = c;
        this.number = number;
    }
    public void run() {
        int value = 0;
        for (int i = 0; i < 10; i++) {
            value = g.get();
            System.out.println("Consumer #"
                    + this.number
                    + " got: " + value);
        }
    }
}

class Producer extends Thread {
    private Godown godown;
    private int number;

    public Producer(Godown g, int number) {
        godown = g;
        this.number = number;
    }

    public void run() {
        for (int i = 0; i < 10; i++) {
            godown.put(i);
            System.out.println("Producer #" + this.number
                    + " put: " + i);
            try {
                sleep((int)(Math.random() * 100));
            } catch (InterruptedException e) { }
        }
    }
}
