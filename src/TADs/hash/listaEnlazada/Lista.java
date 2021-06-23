package TADs.hash.listaEnlazada;

public interface Lista<K, V> {
     void add(K key, V value);
     void remove(K key);
     V get(K key);
     void addFisrt(K key, V value);
     void addLast(K key,V value);
}
