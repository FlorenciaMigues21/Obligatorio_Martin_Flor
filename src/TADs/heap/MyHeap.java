package TADs.heap;

public interface MyHeap<K extends Comparable<K>,T> {

    void insert(K key,T value) throws InvalidInformation;

    T delete() throws EmptyHeapExcepcion; // No le paso nada porque siempre quiero la raiz

    T get() throws EmptyHeapExcepcion;

    int size();

    void visualizar();

}
