package TADs.queue;

public interface MyPriorityQueue<T> extends MyQueue<T>{
     void enqueueWithPriority(T element, int prioridad); // Inserta de acuerdo a laprioridad; si hay mas de un elemento con la misma prioridad, inserta al final
}
