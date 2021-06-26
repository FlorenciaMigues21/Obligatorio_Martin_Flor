package TADs.arraylist;

import entities.CastMember;

public class ArrayList<T> implements ListaArray<T> {

    private int size;
    private T[] list;
    //private final int DEFAULT_INITIAL_ARRAYLIST_SIZE = 5;


    public ArrayList(int size) {
        this.size = 0;
        this.list = (T[]) new Object[size];
    }


    private T[] creadorArrayGenerics(int size) {
        return (T[]) new Object[size];
    }


    public void setList(T[] list) {
        this.list = list;
    }

    @Override
    public void add(T value, int position) {
        if (this.list.length == size) {
            T[] nuevaArrayList = creadorArrayGenerics(this.list.length * 2);
            for (int i = 0; i < this.list.length; i++) {
                nuevaArrayList[i] = this.list[i];
            }
            System.out.println("PROBLEMA");
            this.setList(nuevaArrayList);
        }
        this.list[position] = value;
        if(this.list[position] == null){
            size++;
        }

    }

    @Override
    public void addFirst(T value) {
        //no necesito
    }

    @Override
    public void addLast(T value) {
        if (this.list.length == size) {
            T[] nuevaArrayList = creadorArrayGenerics(this.list.length * 2);
            for (int i = 0; i < this.list.length; i++) {
                nuevaArrayList[i] = this.list[i];
            }
            System.out.println("PROBLEMA ARRAYLIST");
            this.setList(nuevaArrayList);
        } //Pocas veces deberia suceder en este obligatorio
        this.list[size] = value;
        size++;
    }

    @Override
    public int find(T value) {
        int encontre = -1;
        int posicion = 0;

        while (encontre == -1 && posicion < this.size) {
            if (this.list[posicion].equals(value)) {
                encontre = posicion;
            }
            posicion++;
        }

        return encontre;
    }

    @Override
    public void remove(int position) {
        if(position > 0 && position < this.list.length){
            this.list[position] = null;
            size--;
        }
        // Sino ignoro
    }

    @Override
    public T get(int position) {
        return this.list[position];
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public T findValue(T value) {
        // No lo necesito
        return null;
    }

    @Override
    public boolean replaceOrAddLast(T value) {
        // No lo necesito
        return false;
    }

    @Override
    public boolean removeElement(T value) {
        // No lo necesito
        return false;
    }

    @Override
    public void addPisando(T value, int position) { // OJO NO CAMBIA EL SIZE
        if(this.list[position] == null){
            size++;
        }

        this.list[position] = value;
    }

    @Override
    public void intercambiarMedio(int pos){
        // Cambio comun
            T temp2 = this.list[pos];
            this.list[pos] = this.list[pos - 1];
            this.list[pos-1] = temp2;
        }
}


