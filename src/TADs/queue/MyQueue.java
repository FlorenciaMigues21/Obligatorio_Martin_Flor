package TADs.queue;

public interface MyQueue<T> {
    void enqueue (T element);
    T dequeue () throws EmptyQueueException;
    boolean isEmpty();
    int getSize();
}
