package server;

import java.util.Optional;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.TimeUnit;

public class Message {

    private BlockingQueue<Response> queue = new SynchronousQueue<>();

    public Message() {
    }

    public Response pop() {
        try {
            return queue.take();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void push(Response message) {
        try {
            this.queue.put(message);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
