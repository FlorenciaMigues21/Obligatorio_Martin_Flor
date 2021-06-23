package TADs.listaEnlazada;

public interface Lista<T> {
     void add(T value);
     void remove(int position);
     T get(int position);
     void addFisrt(T value);
     void addLast(T value);
     void addposition(T value,int position);
}
