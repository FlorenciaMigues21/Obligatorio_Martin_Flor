package TADs.heap;

public class MyHeapImpl <K extends Comparable<K>,T> implements MyHeap<K,T> {

    private NodeHeap<K,T>[] values;
    private int size = 0;
    private boolean maximo; // Si es true, va a ser maximo, mientras que si es false va a ser minimo


    public MyHeapImpl(int size, boolean tipo) {
        this.values = new NodeHeap[size];
        this.maximo = tipo;
    }

    public NodeHeap<K, T>[] getValues() {
        return values;
    }

    public void setValues(NodeHeap<K, T>[] values) {
        this.values = values;
    }

// Me hago operaciones para obtener las posiciones de cada uno de los hijos y un padre segun las ecuaciones
    // Observacion: En las siguientes operaciones estoy haciendo una division entera entonces por estar en un lenguaje como Java ya me toma el piso

    public int obtenerPosicionHijoDerecho(int posicion) {
        return (2 * posicion + 2);
    }

    public int obtenerPosicionHijoIzquierdo(int posicion) {
        return (2 * posicion + 1);
    }

    public int obtenerPosicionPadre(int posicion) {
        return ((posicion - 1) / 2);
    }


    @Override
    public void insert(K key,T value)  {


        if(this.size == this.values.length){
            NodeHeap<K,T>[] valuesnuevo = new NodeHeap[size * 2];
            for (int i = 0; i<this.values.length; i++) {
                valuesnuevo[i] = this.values[i];
            }
            this.setValues(valuesnuevo);
        }


        NodeHeap<K,T> nodoAgregar = new NodeHeap<>(key, value);
        // Primero para respetar la definicion de arbol completo primero lo meto en el unico lugar donde lo podria meter
        int position = size;
        size++;
        this.values[position] = nodoAgregar;

        if(this.maximo){ // Me fijo si es un heap maximo
            while (position >= 0 && (values[position].getKey().compareTo(values[obtenerPosicionPadre(position)].getKey())) > 0) { // El valor que meti es mas grande que el padre y tengo que hacer un SWAP

                values[position] = values[obtenerPosicionPadre(position)];
                values[obtenerPosicionPadre(position)] = nodoAgregar;

                position = obtenerPosicionPadre(position);
            }
        }else { // En este caso seria un heap minimo
            while (position >= 0 && (values[position].getKey().compareTo(values[obtenerPosicionPadre(position)].getKey())) < 0) { // El valor que meti es mas grande que el padre y tengo que hacer un SWAP

                values[position] = values[obtenerPosicionPadre(position)];
                values[obtenerPosicionPadre(position)] = nodoAgregar;

                position = obtenerPosicionPadre(position);
            }
        }
    }

    @Override
    public T delete() {


        T devolucion = values[0].getValue(); //Aca guardo lo que voy a devolver

        if(this.size == 1){
            values[0] = null; //Solo tenia un elemento en el vector
            size--;
        }else{
            int posicion = 0;
            values[posicion] = values[size - 1]; // Cambio la raiz del elemento que quiero cambiar por el ultimo elelemnto que agregue que es el que puedo sacar y que el arbol siga siendo completo
            values[size - 1] = null;
            size--;


            NodeHeap<K,T> temp = values[posicion];
            int temp2 = -1;
            if(this.maximo){ // En este caso es un heap maximo
                while (size - 1 >= (posicion * 2 + 1) && posicion < size && values[posicion].getKey().compareTo(values[posicionDelHijoMayor(posicion)].getKey()) < 0) { // El valor que intercambie es menos que el mayor de sus hijos

                    temp2 = posicionDelHijoMayor(posicion);
                    values[posicion] = values[temp2];
                    values[temp2] = temp;

                    posicion = temp2;
                }
            }else{ // Este es el caso de que sea un heap minimo
                while (size - 1 >= (posicion * 2 + 1) && posicion < size && values[posicion].getKey().compareTo(values[posicionDelHijoMenor(posicion)].getKey()) > 0) { // El valor que intercambie es menos que el mayor de sus hijos

                    temp2 = posicionDelHijoMenor(posicion);
                    values[posicion] = values[temp2];
                    values[temp2] = temp;

                    posicion = temp2;
                }
            }
        }
        return devolucion;
    }

    public int posicionDelHijoMayor(int posicion) { //Esta operacion me da la posicion del mayor de los hijos de un determinado nodo
        if (posicion < 0 || posicion >= size) { //Evito null PointerException
            throw new RuntimeException();
        }
        if (size - 1 < (2 * posicion + 1)) { // Siempre que el izquiero sea null el derecho es null. Siempre que tenga hijo derecho tiene izquierdo
            return -1; // Nunca -1 va a ser la posicion de algo en un vector
        }else{
            if (size - 1 < (2 * posicion + 2)) { // Puede pasar que el derecho sea null y el izquierdo no
                return obtenerPosicionHijoIzquierdo(posicion);
            }else{ // En este caso ninguno de los dos es null
                if (values[obtenerPosicionHijoDerecho(posicion)].getKey().compareTo(values[obtenerPosicionHijoIzquierdo(posicion)].getKey()) > 0) { // En este caso significa que el hijo izquiero es mas chico que lel derecho como en el ABB
                    return obtenerPosicionHijoDerecho(posicion);
                } else {
                    return obtenerPosicionHijoIzquierdo(posicion);
                }
            }
        }
    }

    public int posicionDelHijoMenor(int posicion) {
        if (posicion < 0 || posicion >= size) { //Evito null PointerException
            throw new RuntimeException();
        }
        if (size - 1 < (2 * posicion + 1)) { // Siempre que el izquiero sea null el derecho es null. Siempre que tenga hijo derecho tiene izquierdo
            return -1; // Nunca -1 va a ser la posicion de algo en un vector
        }else{
            if (size - 1 < (2 * posicion + 2)) { // Puede pasar que el derecho sea null y el izquierdo no
                return obtenerPosicionHijoIzquierdo(posicion);
            }else{ // En este caso ninguno de los dos es null
                if (values[obtenerPosicionHijoDerecho(posicion)].getKey().compareTo(values[obtenerPosicionHijoIzquierdo(posicion)].getKey()) < 0) { // En este caso significa que el hijo izquiero es mas chico que lel derecho como en el ABB
                    return obtenerPosicionHijoDerecho(posicion);
                } else {
                    return obtenerPosicionHijoIzquierdo(posicion);
                }
            }
        }
    }

    @Override
    public T get() throws EmptyHeapExcepcion {

        if(this.size == 0){  // En este caso no tengo elementos en el Heap
            throw new EmptyHeapExcepcion();
        }

        return values[0].getValue();
    }

    @Override
    public int size() {
        return (this.size);
    }

    @Override
    public void visualizar(){
        int nivel = 0;
        int contador = 0;
        for(int i = 0; i < this.size ; i++){ // Voy corriendome en el array
            if(contador >= Math.pow(2, nivel)){
                nivel++;
                contador = 0;
                System.out.println();
            }
            System.out.print(values[i].getValue() + "   ");
            contador++;
        }
        System.out.println();
    }

    public static void main(String[] args) throws EmptyHeapExcepcion, InvalidInformation {
        MyHeap<Integer,Integer> heapMaximo = new MyHeapImpl<>(20,true); // Me estoy crando un Heap Maximo de enteros
        MyHeap<Integer,Integer> heapMinimo = new MyHeapImpl<>(20,false); // Me estoy crando un Heap Minimo de enteros


        System.out.println("Pruebas de insert para heap maximo");

        heapMaximo.insert(9,9);
        heapMaximo.insert(72,72);
        heapMaximo.insert(24,24);
        heapMaximo.insert(12,12);
        heapMaximo.insert(2,2);
        heapMaximo.insert(555,555);
        heapMaximo.insert(14,14);
        heapMaximo.insert(73,73);
        heapMaximo.insert(10,10);
        heapMaximo.insert(32,32);
        heapMaximo.insert(0,0);
        heapMaximo.insert(-2,-2);

        //try{
        //    heapMaximo.insert(-2,-2);
        //}catch (InvalidInformation e){
        //    System.out.println("De manera correcta no me deja agregar elementos que ya esten en el arbol");
        //}


        if(heapMaximo.size() == 12){
            System.out.println("El insert parece funcionar bien para los heaps maximos");
        }else{
            System.out.println("Hay un problema para los heaps minimos");
        }

        System.out.println("Pruebas de insert para heap minimo");

        heapMinimo.insert(9,9);
        heapMinimo.insert(72,72);
        heapMinimo.insert(24,24);
        heapMinimo.insert(12,12);
        heapMinimo.insert(2,2);
        heapMinimo.insert(555,555);
        heapMinimo.insert(14,14);
        heapMinimo.insert(73,73);
        heapMinimo.insert(7,7);
        heapMinimo.insert(32,32);
        heapMinimo.insert(0,0);
        heapMinimo.insert(-2,-2);
        heapMinimo.insert(74,74);

        if(heapMinimo.size() == 13){
            System.out.println("El insert parece funcionar bien para los heaps minimos");
        }else{
            System.out.println("Hay un problema con el insert para los heaps minimos");
        }




        System.out.println("Pruebas del delete para heap maximo");
        System.out.println(heapMaximo.delete());
        System.out.println(heapMaximo.delete());
        System.out.println(heapMaximo.delete());
        System.out.println(heapMaximo.delete());
        System.out.println(heapMaximo.delete());
        System.out.println(heapMaximo.delete());
        System.out.println(heapMaximo.delete());
        System.out.println(heapMaximo.delete());
        System.out.println(heapMaximo.delete());
        System.out.println(heapMaximo.size());
        System.out.println(heapMaximo.delete());
        System.out.println(heapMaximo.delete());
        System.out.println(heapMaximo.delete()); // Debe imprimir 555 73 72 32 24 14 12 10 9 2 0 -2
        //System.out.println(heapMaximo.delete()); // Si dejo este me tendria que saltar un error de Heap vacio

        System.out.println("Pruebas del delete para heap minimo");
        System.out.println(heapMinimo.delete());
        System.out.println(heapMinimo.delete());
        System.out.println(heapMinimo.delete());
        System.out.println(heapMinimo.delete());
        System.out.println(heapMinimo.delete());
        System.out.println(heapMinimo.delete());
        System.out.println(heapMinimo.delete());
        System.out.println(heapMinimo.delete());
        System.out.println(heapMinimo.delete());
        System.out.println(heapMinimo.delete());
        System.out.println(heapMinimo.delete());
        System.out.println(heapMinimo.delete());
        System.out.println(heapMinimo.delete());// -2 2 0 9 7 14 24 73 72 32 12 555 74
        //System.out.println(heapMinimo.delete()); // Si dejo este me tendria que saltar un error de Heap vacio

        if(heapMaximo.size() == 0 && heapMinimo.size() == 0){
            System.out.println("Parece funcionar bien el delete");
        }else {
            System.out.println("Hay problemas al menos con el decremento del tamaño en el delete");
            System.out.println("El tamaño del heapMaximo es " + heapMaximo.size());
            System.out.println("El tamaño del heapMinimo es " + heapMinimo.size());
        }

        System.out.println("Pruebas para la visualizacion de los arboles");

        System.out.println("Recupero los arboles que borre");
        heapMaximo.insert(9,9);
        heapMaximo.insert(72,72);
        heapMaximo.insert(24,24);
        heapMaximo.insert(12,12);
        heapMaximo.insert(2,2);
        heapMaximo.insert(555,555);
        heapMaximo.insert(14,14);
        heapMaximo.insert(73,73);
        heapMaximo.insert(10,10);
        heapMaximo.insert(32,32);
        heapMaximo.insert(0,0);
        heapMaximo.insert(-2,-2);

        heapMinimo.insert(9,9);
        heapMinimo.insert(72,72);
        heapMinimo.insert(24,24);
        heapMinimo.insert(12,12);
        heapMinimo.insert(2,2);
        heapMinimo.insert(555,555);
        heapMinimo.insert(14,14);
        heapMinimo.insert(73,73);
        heapMinimo.insert(7,7);
        heapMinimo.insert(32,32);
        heapMinimo.insert(0,0);
        heapMinimo.insert(-2,-2);
        heapMinimo.insert(74,74);

        System.out.println("Hago las pruebas para visualsar");
        heapMaximo.visualizar();
        heapMinimo.visualizar();


    }
}








