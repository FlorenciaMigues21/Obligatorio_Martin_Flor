package TADs.heap;

public interface MyHeap<K extends Comparable<K>,T> {

    void insert(K key,T value);

    T delete(); // No le paso nada porque siempre quiero la raiz

    T get() throws EmptyHeapExcepcion;

    int size();

    void visualizar();

}
