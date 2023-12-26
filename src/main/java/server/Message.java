package server;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.SynchronousQueue;

public class Message {

    private BlockingQueue<String> queue = new SynchronousQueue<>();

    public Message() {
    }

    public String pop() {
        try {
            return queue.take();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void push(String message) {
        try {
            this.queue.put(message);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
