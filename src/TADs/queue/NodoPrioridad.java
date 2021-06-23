package TADs.queue;

public class NodoPrioridad <T> {
    public T value;
    public NodoPrioridad<T> siguiente;
    public int prioridad;

    public NodoPrioridad(T value, int prioridad) {
        this.value = value;
        this.siguiente = null;
        this.prioridad = prioridad;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public NodoPrioridad<T> getSiguiente() {
        return siguiente;
    }

    public void setSiguiente(NodoPrioridad<T> siguiente) {
        this.siguiente = siguiente;
    }

    public int getPrioridad() {
        return prioridad;
    }

    public void setPrioridad(int prioridad) {
        this.prioridad = prioridad;
    }
}
