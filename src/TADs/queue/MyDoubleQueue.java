package TADs.queue;

public interface MyDoubleQueue<T> {
    void enqueueLeft(T element);
    T dequeueLeft(T element) throws EmptyQueueException;
    void enqueueRight(T element);
    T dequeueRight () throws EmptyQueueException;
    boolean isEmpty();
    int getSize();
}
