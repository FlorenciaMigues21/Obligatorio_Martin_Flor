package TADs.listaEnlazada;

public class ListaEnlazada<T> implements Lista<T> {

    public Nodo<T> primerElemento = null;
    public Nodo<T> ultimoElemento = null;
    public int size = 0;


    public int getsize() {
        return size;
    }


    @Override
    public void add(T value) {
        Nodo<T> nodoAAgregar = new Nodo<T>(value);
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
                    //Aca ya excluyo el primer elemento?
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

    public boolean encontrarElemento(Object elemento) {
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
        Nodo<T> nuevoPrimerNodo = new Nodo<T>(value);
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
        Nodo<T> nodoAAgregar = new Nodo<T>(value);
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


    public void visualizar(ListaEnlazada<T> listaEnteros) {
        for (int i = 0; i < listaEnteros.getsize(); i++) {
            //Para que no me i mprima null y que la posicion no existe en el vector
            if (this.get((int) listaEnteros.get(i)) != null) {
                System.out.println(this.get((int) listaEnteros.get(i)));//Asumo que la lista es de enteros positivos, tiene sentido-ver letra
            }
        }
    }


    public static void main(String[] args) {

        ListaEnlazada<Integer> listaDePrueba = new ListaEnlazada<>();
        listaDePrueba.add(3);
        listaDePrueba.add(5);
        listaDePrueba.add(7);
        listaDePrueba.add(11);
        listaDePrueba.remove(11);
        listaDePrueba.remove(3);
        if (listaDePrueba.size == 3) {
            System.out.println("OK");
        } else {
            System.out.println("ERROR");
        }
        listaDePrueba.get(3);
        System.out.println(listaDePrueba.size);
        System.out.println(listaDePrueba.get(2));
        Nodo<Integer> nodo1 = new Nodo<>(3);
        Nodo<Integer> nodo2 = new Nodo<>(1);
        Nodo<Integer> nodo3 = new Nodo<>(1);
        //Prueba Add ascending
        System.out.println("PRUEBAS DE ADD ASCENDING PARA DOUBLE LINKED LIST");
        DobleListaEnlazada<Integer> listaDePruebaDos = new DobleListaEnlazada<>();
        listaDePruebaDos.addascendente(10); // 10
        listaDePruebaDos.addascendente(5); // 8 10
        listaDePruebaDos.addascendente(14); // 8 10 14
        listaDePruebaDos.addascendente(13); // 8 10 13 14
        listaDePruebaDos.addascendente(25); // 8 10 13 14 25
        listaDePruebaDos.addascendente(2); // 2 8 10 13 14 25
        listaDePruebaDos.addascendente(200); // 2 8 10 13 14 25 200
        listaDePruebaDos.addascendente(11); // 2 8 10 11 13 14 25 200
        listaDePruebaDos.addascendente(200); // 2 8 10 13 14 25 200 200
        //listaDePruebaDos.addascendente(11); // 2 8 10 11 11 13 14 25 200 200
        listaDePruebaDos.addascendente(3); // 2 2 8 10 11 11 13 14 25 200 200
        listaDePruebaDos.addascendente(300); // 2 2 8 10 11 11 13 14 25 200 200
        System.out.println("interfaces.Lista tamaño: " + listaDePruebaDos.getsize()); // imprime 11
        for (int i = 0; i < listaDePruebaDos.getsize(); i++) {
            System.out.println(listaDePruebaDos.get(i));
        }

        //PRUEBAS
        System.out.println("Pruebas addLast para DOUBLE LINKED LIST");
        DobleListaEnlazada<Integer> listaDePrueba3 = new DobleListaEnlazada<>();
        listaDePrueba3.addLast(3); // 3
        listaDePrueba3.addLast(5); // 3 5
        listaDePrueba3.addLast(7); // 3 5 7
        listaDePrueba3.addLast(11); // 3 5 7 11
        for (int i = 0; i < listaDePrueba3.getsize(); i++) {
            System.out.println(listaDePrueba3.get(i));
        }
        listaDePrueba3.remove(15); //este debe dar ERROR
        listaDePrueba3.remove(3); // 3 5 7
        for (int i = 0; i < listaDePrueba3.getsize(); i++) {
            System.out.println(listaDePrueba3.get(i));
        }
        if (listaDePrueba3.getsize() == 3) {
            System.out.println("OK"); //debe imprimir OK
        } else {
            System.out.println("ERROR");
        }
        listaDePrueba3.get(3); //este debe dar ERROR
        System.out.println(listaDePrueba3.getsize()); // imprime 3
        System.out.println(listaDePrueba3.get(2)); // imprime 7
        System.out.println(listaDePrueba3.encontrarElemento(7)); // imprime true
        System.out.println(listaDePrueba3.encontrarElemento(15)); // imprime false
        //Prueba ADD por posicion
        System.out.println("------------------------");
        System.out.println(listaDePrueba3.getsize());
        for (int i = 0; i < listaDePrueba3.getsize(); i++) {
            System.out.println(listaDePrueba3.get(i));
        }
        listaDePrueba3.addposition(10, 1); // 3 10 5 7
        System.out.println("------------------------");
        System.out.println(listaDePrueba3.getsize());
        for (int i = 0; i < listaDePrueba3.getsize(); i++) {
            System.out.println(listaDePrueba3.get(i));
        }
        listaDePrueba3.addposition(14, 0); // 14 3 10 5 7
        System.out.println("------------------------");
        System.out.println(listaDePrueba3.getsize());
        for (int i = 0; i < listaDePrueba3.getsize(); i++) {
            System.out.println(listaDePrueba3.get(i));
        }
        listaDePrueba3.addposition(20, 10); // 14 3 10 5 7 //La posicion en la que se quiere insertar el elemento es inaceptable. yndes aut of baundz
        listaDePrueba3.addposition(15, 5); // 14 3 10 5 7 15
        listaDePrueba3.addposition(22, 7); // 14 3 10 5 7 15
        System.out.println(listaDePrueba3.getsize()); // imprime 6
        for (int i = 0; i < listaDePrueba3.getsize(); i++) {
            System.out.println(listaDePrueba3.get(i));
        }

        //Pruebas para comparacion de lsitas
        System.out.println("-----------------------");
        System.out.println("La lListaDePrueba3 es:");
        for (int i = 0; i < listaDePrueba3.getsize(); i++) {
            System.out.println(listaDePrueba3.get(i));
        }
        System.out.println("La ListaDePruebaDos es:");
        for (int i = 0; i < listaDePruebaDos.getsize(); i++) {
            System.out.println(listaDePruebaDos.get(i));
        }

        System.out.println("-----------------------");

        listaDePruebaDos.comparacionListas(listaDePrueba3);

        listaDePrueba3.comparacionListas(listaDePruebaDos);

        listaDePruebaDos.comparacionListas(listaDePruebaDos);
/*
        DobleListaEnlazada listaAOrdenar = new DobleListaEnlazada();
        listaAOrdenar.add(12);
        listaAOrdenar.add(7);
        listaAOrdenar.add(45);
        listaAOrdenar.add(687);
        listaAOrdenar.add(2);
        listaAOrdenar.add(2);
        listaAOrdenar.add(4);
        listaAOrdenar.add(56);
        listaAOrdenar.add(55);
        listaAOrdenar.add(6);
        listaAOrdenar.add(1);
        listaAOrdenar.add(0);
        listaAOrdenar.ordenarLista();
        System.out.println("-----------------------");

        for (int i = 0; i < listaAOrdenar.getsize(); i++) {
            System.out.println(listaAOrdenar.get(i));
        }
        */
        System.out.println("PRUEBAS DEL EJERCICIO 8");
        ListaEnlazada<Integer> listaEnteros = new ListaEnlazada<>();
        listaEnteros.add(3);
        listaEnteros.add(1);
        listaEnteros.add(9);
        listaEnteros.add(3);
        listaEnteros.add(7);
        listaEnteros.add(-1);//Debe dar error
        listaEnteros.add(11);//Debe dar error
        //listaDePruebaTres.visualizar(listaEnteros);

        System.out.println("PRUEBAS DEL EJERCICIO 9");

        listaDePruebaDos.intercanbiar(2, -1);//aca me debe dar error porque no hay con quien cambiar el nodo

        listaDePruebaDos.intercanbiar(2, 1);
        for (int i = 0; i < listaDePruebaDos.getsize(); i++) {
            System.out.println(listaDePruebaDos.get(i));
        }
        listaDePruebaDos.intercanbiar(3, -1);//2ahora es el primero por lo que es logico que no haya nodo para cambiarlo

        listaDePruebaDos.intercanbiar(300, -1);
        for (int i = 0; i < listaDePruebaDos.getsize(); i++) {
            System.out.println(listaDePruebaDos.get(i));
        }

        listaDePruebaDos.intercanbiar(25, 1);//aca me debe dar error porque no hay con quien cambiar el nodo

        listaDePruebaDos.intercanbiar(177, 1);//Aca me debe dar error porque no existe el elemento
        for (int i = 0; i < listaDePruebaDos.getsize(); i++) {
            System.out.println(listaDePruebaDos.get(i));
        }
        listaDePruebaDos.intercanbiar(13, 1);//Aca cambio uno comun del medio
        for (int i = 0; i < listaDePruebaDos.getsize(); i++) {
            System.out.println(listaDePruebaDos.get(i));
        }
        listaDePruebaDos.intercanbiar(13, 0);

        DobleListaEnlazada<Integer> listaIntercambiar = new DobleListaEnlazada<>();
        listaIntercambiar.add(1);
        listaIntercambiar.add(2);
        for (int i = 0; i < listaIntercambiar.getsize(); i++) {
            System.out.println(listaIntercambiar.get(i));
        }
        listaIntercambiar.intercanbiar(1, 1);
        for (int i = 0; i < listaIntercambiar.getsize(); i++) {
            System.out.println(listaIntercambiar.get(i));
        }
        listaIntercambiar.intercanbiar(1, 1);
        for (int i = 0; i < listaIntercambiar.getsize(); i++) {
            System.out.println(listaIntercambiar.get(i));
        }
        listaIntercambiar.intercanbiar(1, -1);
        for (int i = 0; i < listaIntercambiar.getsize(); i++) {
            System.out.println(listaIntercambiar.get(i));
        }
        listaIntercambiar.intercanbiar(1, -1);

/*
        System.out.println("Pruebas para ordenar");

        DobleListaEnlazada<Integer> listapruebacinco = new DobleListaEnlazada<>();
        listapruebacinco.add(5);
        listapruebacinco.add(7);
        listapruebacinco.add(7);
        listapruebacinco.add(8);
        listapruebacinco.add(8);
        listapruebacinco.add(1);
        listapruebacinco.add(2);
        listapruebacinco.add(12);
        listapruebacinco.add(3);
        listapruebacinco.add(79);
        listapruebacinco.add(36);
        listapruebacinco.add(128);
        listapruebacinco.add(2);
        for (int i = 0; i < listapruebacinco.getsize(); i++) {
            System.out.println(listapruebacinco.get(i));
        }
        System.out.println("Ordeno al lista");
        //listapruebacinco.ordenar();
        for (int i = 0; i < listapruebacinco.getsize(); i++) {
            System.out.println(listapruebacinco.get(i));
        }

        System.out.println("-------------------------------");
*/
    }
}