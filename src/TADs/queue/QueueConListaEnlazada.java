package TADs.queue;

import TADs.listaEnlazada.Lista;
import TADs.listaEnlazada.ListaEnlazada;
import TADs.listaEnlazada.Nodo;

public class QueueConListaEnlazada<T> implements MyQueue<T>, Lista<T> {

    public Nodo<T> primerElemento = null;
    public Nodo<T> ultimoElemento = null;
    public int size = 0;




    @Override
    public void add(T value) {
        Nodo<T> nodoAAgregar = new Nodo<>(value);
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
    public void remove(int position) {
        if (position > size - 1 || position < 0) {
            System.out.println("La posicion es no valida");
        } else {
            if (position == 0) {
                if (size > 1) {
                    primerElemento = primerElemento.getSiguiente();
                } else {
                    primerElemento = null;
                    ultimoElemento = null;
                }
            } else {
                int i = 0;
                Nodo<T> miNodoABorrar = primerElemento;
                Nodo<T> miNodoAnterior = null;
                Nodo<T> miNodoSiguiente = null;
                while (i < position) {
                    miNodoAnterior = miNodoABorrar;
                    miNodoABorrar = miNodoABorrar.getSiguiente();
                    miNodoSiguiente = miNodoABorrar.getSiguiente();
                    // Tengo que saber mi nodo anterior para arreglarle la flechita
                    i++;
                }
                if (position == (size - 1)) {
                    ultimoElemento = miNodoAnterior;
                }
                //Aca cambio la flechita
                miNodoAnterior.setSiguiente(miNodoSiguiente);
            }
            size--;
        }
    }

    @Override
    public T get(int position) {
        T devolucion;
        if (position > size - 1 || position < 0) {
            System.out.println("La posicion no existe en este vector");
            devolucion = null;
        } else {
            int contador = 0;
            Nodo<T> nodoBuscado = primerElemento;
            //En la posicion n-1 entro al while, lo cambio y cuando estoy con el contador en n pregunto para entrar y me dice que no pero ya tengo al nodo buscado como el que esta en la posicion n
            while (contador < position) {
                nodoBuscado = nodoBuscado.getSiguiente();
                contador++;
            }
            devolucion = nodoBuscado.getValue();
        }
        return devolucion;
    }

    public boolean encontrarElemento(T elemento) {
        Nodo<T> temp = primerElemento;
        for (int i = 0; i < size - 1; i++) {
            if (temp.getValue().equals(elemento)) {
                return true;
            }
            temp = temp.getSiguiente();
        }
        return false;
    }

    public void addFisrt(T value) {
        Nodo<T> nuevoPrimerNodo = new Nodo<>(value);
        if (primerElemento == null) {
            primerElemento = nuevoPrimerNodo;
            ultimoElemento = nuevoPrimerNodo;
        } else {
            Nodo<T> nuevoSegundoNodo = primerElemento;
            primerElemento = nuevoPrimerNodo;
            nuevoPrimerNodo.setSiguiente(nuevoSegundoNodo);
        }
        size++;
    }

    public void addLast(T value) {
        this.add(value);
    }

    @Override
    public void addposition(T value, int position) {
        Nodo<T> nodoAAgregar = new Nodo<>(value);
        if (position < 0 || position > size) {
            System.out.println("La posicion en la que se quiere insertar el elemento es inaceptable. yndes aut of baundz");
        } else {
            if (position == 0) {
                addFisrt(value);
            } else {
                if (position == size) {
                    addLast(value);
                } else {
                    int i = 0;
                    Nodo<T> nodoAnterior = primerElemento;
                    while (i < position - 1) {
                        nodoAnterior = nodoAnterior.getSiguiente();
                        i++;
                    }
                    nodoAAgregar.setSiguiente(nodoAnterior.getSiguiente());
                    nodoAnterior.setSiguiente(nodoAAgregar);
                    size++;
                }
            }
        }
    }
/*
    @Override
    public void addascendente(T value) {
        Nodo<T> nodoAAgregar = new Nodo<>(value);
        if (primerElemento == null) {
            addFisrt(value);
        } else {
            int valorDelNodo = (int) nodoAAgregar.getValue();//Valor del nodo que quiero agregar
            Nodo<T> nodoPrevioAAgregar = primerElemento;
            while (nodoPrevioAAgregar.getSiguiente() != null && (int) nodoPrevioAAgregar.getSiguiente().getValue() < valorDelNodo) {//Lo pongo en este orden pa no tener problema de comparacion con null, se corre de izquiera a derecha.
                nodoPrevioAAgregar = nodoPrevioAAgregar.getSiguiente();
            }
            if (nodoPrevioAAgregar == primerElemento && (int) nodoPrevioAAgregar.getValue() > valorDelNodo) {//Falto evaluarlo en el while
                addFisrt(value);
            } else {
                if (nodoPrevioAAgregar.getSiguiente() == null) {
                    addLast(value);
                } else {
                    nodoAAgregar.setSiguiente(nodoPrevioAAgregar.getSiguiente());
                    nodoPrevioAAgregar.setSiguiente(nodoAAgregar);
                    size++;
                }
            }
        }
    }
*/
    public void visualizar(ListaEnlazada<Integer> listaEnteros) {
        for (int i = 0; i < listaEnteros.getsize(); i++) {
            //Para que no me i mprima null y que la posicion no existe en el vector
            if (this.get(listaEnteros.get(i)) != null) {
                System.out.println(this.get(listaEnteros.get(i)));//Asumo que la lista es de enteros positivos, tiene sentido-ver letra
            }
        }
    }

    //A PARTIR DE ACA ESTA LO NUEVO

    @Override
    public void enqueue(T element) {
        this.addLast(element);
    }

    @Override
    public T dequeue() throws EmptyQueueException {
        if(this.getSize()==0){
            throw new EmptyQueueException();
        }
        T devolucion = this.get(0);
        this.remove(0);
        return devolucion;
    }

    @Override
    public boolean isEmpty() {
        return (this.getSize()==0);
    }

    @Override
    public int getSize() {
        return (this.size);
    }
}