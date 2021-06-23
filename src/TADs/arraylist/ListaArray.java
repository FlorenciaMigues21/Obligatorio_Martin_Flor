package TADs.arraylist;

public interface ListaArray<T>  {

    void add(T value, int posicion);
    void remove(int position);
    T get(int position);
    void addFirst(T value);
    void addLast(T value);
    int find(T value);
    int size();
    T findValue(T value);
    boolean replaceOrAddLast(T value);
    boolean removeElement(T value);
    void addPisando(T value, int position);

}
