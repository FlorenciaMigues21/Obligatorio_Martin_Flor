package TADs.hash.listaEnlazada;

import TADs.heap.InvalidInformation;

public class ListaEnlazada<K,V> implements Lista<K, V> {

    public Nodo<K, V> primerElemento = null;
    public Nodo<K, V> ultimoElemento = null;
    public int size = 0;

    public int getsize() {
        return size;
    }

    @Override
    public void add(K key, V value) {
        Nodo<K, V> nodoAAgregar = new Nodo<>(key,value);
        if (this.primerElemento == null) {
            this.primerElemento = nodoAAgregar;
            //Siempre la primera vez que agrego el primer elemento va a ser el ultimo tambien
            this.ultimoElemento = nodoAAgregar;
        } else {
            ultimoElemento.setSiguiente(nodoAAgregar);
            ultimoElemento = nodoAAgregar;
        }
        size++;

    }

    @Override
    public void remove(K key) {
        boolean seBorro = false;
        if (this.size == 1) {
            if (primerElemento.getKey().equals(key)) {
                primerElemento = null;
                ultimoElemento = null;
                seBorro = true;
            }
        }else if (this.getsize() > 1){
            if (primerElemento.getKey().equals(key)) {
                primerElemento = primerElemento.getSiguiente();
                seBorro = true;
            } else {
                Nodo<K, V> miNodoAnterior2 = primerElemento;
                int contador = this.getsize() - 1;
                while (0 < contador) {
                    if (miNodoAnterior2.getSiguiente().getKey().equals(key)) {
                        if (miNodoAnterior2.getSiguiente().getSiguiente() == null) {
                            ultimoElemento = miNodoAnterior2;
                        }
                        miNodoAnterior2.setSiguiente(miNodoAnterior2.getSiguiente().getSiguiente());
                        seBorro = true;
                        break;
                    }
                    miNodoAnterior2 = miNodoAnterior2.getSiguiente();
                    contador--;
                }
            }
        }
        size--;
    }

    @Override
    public V get(K key) {
        V devolucion = null;
        int contador = this.getsize();
        Nodo<K,V> nodoBuscado = primerElemento;
        while (contador > 0) {
            if (nodoBuscado.getKey().equals(key)) {
                devolucion = nodoBuscado.getValue();
                break;
            }
            nodoBuscado = nodoBuscado.getSiguiente();
            contador--;
        }
        return devolucion;
    }

    public boolean encontrarElemento(K key) {

        boolean devolucion = false;
        Nodo<K,V> temp = primerElemento;
        for (int i = 0; i < this.getsize(); i++) {
            if (temp.getKey().equals(key)) {
                devolucion = true;
                break;
            }
            temp = temp.getSiguiente();
        }
        return devolucion;
    }

    public void addFisrt(K key, V value) {

        Nodo<K,V> nuevoPrimerNodo = new Nodo(key,value);
        if (primerElemento == null) {
            primerElemento = nuevoPrimerNodo;
            ultimoElemento = nuevoPrimerNodo;
        } else {
            nuevoPrimerNodo.setSiguiente(primerElemento);
            primerElemento = nuevoPrimerNodo;
        }
        size++;
    }

    public void addLast(K key,V value){
        this.add(key,value);
    }

    public void visualizar(){
        Nodo<K,V> temp = primerElemento;
        for(int i = 0; i < this.getsize(); i++){
            System.out.println(temp.getValue());
            temp = temp.getSiguiente();
        }
    }

    public Nodo<K,V> getNodoPosicion(int posicion){
        Nodo<K,V> devolucion = primerElemento;
        for(int i = 0; i < posicion; i++){
            devolucion = devolucion.getSiguiente();
        }
        return devolucion;
    }

    /*public static void main(String[] args) {

        ListaEnlazada<Integer, Integer> prueba1 = new ListaEnlazada<>();
        prueba1.add(0, 0);
        prueba1.add(1, 1);
        prueba1.add(2, 2);
        prueba1.add(3, 3);
        prueba1.add(4, 4);
        prueba1.add(5, 5);
        prueba1.add(6, 6);
        prueba1.add(7, 7);
        prueba1.add(9, 9);
        try {
            prueba1.add(9, 9); // No debe dejarme agregarlo;
            System.out.println("Me esta dejando agregar elemento con la misma key, ERROR");
        } catch (InvalidInformation e) {
            System.out.println("No me esta dejando agregar elementos con la misma key, BIEN");
        }
        prueba1.add(11, 11);
        prueba1.add(222, 222);
        prueba1.add(12, 12);
        prueba1.add(34, 34);
        prueba1.add(55, 55);
        prueba1.add(68, 68);
        prueba1.add(72, 72);
        prueba1.add(97, 97);
        prueba1.add(95, 95);
        if (prueba1.getsize() == 18) {
            System.out.println("El size parece estar funcionando BIEN");
        } else {
            System.out.println("Parece estar habiendo un problema con el size; El size en verdad es : " + prueba1.getsize());
        }
        prueba1.visualizar();

        if (prueba1.get(11) == 11) {
            System.out.println("Parece que el get funciona BIEN");
        } else {
            System.out.println("Hay un PROBLEMA con el get");
        }

        try {
            prueba1.get(285);
        } catch (InvalidInformation e) {
            System.out.println("Esta BIEN que me tire una excepcion si hago un get de algo que no tengo");
        }

        prueba1.remove(95);
        prueba1.remove(0);
        prueba1.remove(1);
        prueba1.remove(72);
        if (prueba1.getsize() == 14) {
            System.out.println("Parece que el remove anda BIEN");
        } else {
            System.out.println("Parece estar habiendo un problema con el remove; El size en verdad es : " + prueba1.getsize());
        }

        try {
            prueba1.remove(285);
        } catch (InvalidInformation e) {
            System.out.println("Esta BIEN que me tire una excepcion si hago un remove de algo que no tengo");
        }
        prueba1.visualizar();

        ListaEnlazada<Integer, Integer> prueba2 = new ListaEnlazada<>();
        prueba2.add(12, 12);
        prueba2.add(15, 15);
        prueba2.get(12);
        prueba2.get(15);
        prueba2.remove(15);
        prueba2.remove(12);
        if (prueba2.getsize() == 0) {
            System.out.println("El size parece estar funcionando BIEN");
        } else {
            System.out.println("Parece estar habiendo un problema con el size; El size en verdad es : " + prueba2.getsize());
        }
        prueba2.visualizar();
    }*/
}








