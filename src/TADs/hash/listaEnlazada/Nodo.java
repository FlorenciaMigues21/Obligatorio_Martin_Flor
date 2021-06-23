package TADs.hash.listaEnlazada;

public class Nodo<K,V> {
    private V value;
    private K key;
    private Nodo<K,V> siguiente;

    public Nodo(K key, V value) {
        this.key = key;
        this.value = value;
        this.siguiente = null;
    }

    public K getKey() {
        return key;
    }

    public void setKey(K key) {
        this.key = key;
    }

    public V getValue() {
        return value;
    }

    public void setValue(V value) {
        this.value = value;
    }

    public Nodo<K,V> getSiguiente() {
        return siguiente;
    }

    public void setSiguiente(Nodo<K,V> siguiente) {
        this.siguiente = siguiente;
    }

}
