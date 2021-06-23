package TADs.listaEnlazada;

public class NodoDLL <T> {

    public T value;
    public NodoDLL<T> siguiente;
    public NodoDLL<T> anterior;

    public NodoDLL(T value) {
        this.value = value;
        this.siguiente = null;
        this.anterior = null;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public NodoDLL<T> getSiguiente() {
        return siguiente;
    }

    public void setSiguiente(NodoDLL<T> siguiente) {
        this.siguiente = siguiente;
    }

    public NodoDLL<T> getAnterior() {
        return anterior;
    }

    public void setAnterior(NodoDLL<T> anterior) {
        this.anterior = anterior;
    }
}
