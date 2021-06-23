package TADs.listaEnlazada;

public class DobleListaEnlazada<T> implements Lista<T> {

    public NodoDLL<T> primerElemento = null;
    public NodoDLL<T> ultimoElemento = null;
    public int size = 0;


    public int getsize() {
        return size;
    }

    @Override
    public void add(T value) {
        NodoDLL<T> nodoAAgregar = new NodoDLL<T>(value);
        if (this.primerElemento == null) {
            this.primerElemento = nodoAAgregar;
            //Siempre la primera vez que agrego el primer elemento va a ser el ultimo tambien
            this.ultimoElemento = nodoAAgregar;
        } else {
            nodoAAgregar.setAnterior(ultimoElemento);
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
                    primerElemento.setAnterior(null);
                } else {
                    primerElemento = null;
                    ultimoElemento = null;
                }
            } else {
                if (position == (size - 1)) {
                    (ultimoElemento.getAnterior()).setSiguiente(null);
                    ultimoElemento = ultimoElemento.getAnterior();
                } else {
                    //En este caso quiero sacar un nodo del diome; CASO GENERAL
                    int i = 0;
                    NodoDLL<T> miNodoABorrar = primerElemento;
                    while (i < position) {
                        miNodoABorrar = miNodoABorrar.getSiguiente();
                        i++;
                    }
                    miNodoABorrar.getAnterior().setSiguiente(miNodoABorrar.getSiguiente());
                    miNodoABorrar.getSiguiente().setAnterior((miNodoABorrar.getAnterior()));
                }
            }
            size--;
        }
    }

    @Override
    public T get(int position) {//¿¿¿Seria bueno optimizarlo viendo de ir de atras para adelante???
        T devolucion;
        if (position > size - 1 || position < 0) {
            System.out.println("La posicion no existe en este vector");
            devolucion = null;
        } else {
            int contador = 0;
            NodoDLL<T> nodoBuscado = primerElemento;
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
        NodoDLL<T> temp = primerElemento;
        for (int i = 0; i < size; i++) {
            if (temp.getValue().equals(elemento)) {
                return true;
            }
            temp = temp.getSiguiente();
        }
        return false;
    }

    @Override
    public void addFisrt(T value) {
        NodoDLL<T> nuevoPrimerNodo = new NodoDLL<>(value);
        if (primerElemento == null) {
            primerElemento = nuevoPrimerNodo;
            ultimoElemento = nuevoPrimerNodo;
        } else {
            nuevoPrimerNodo.setSiguiente(primerElemento);
            primerElemento.setAnterior(nuevoPrimerNodo);
            primerElemento = nuevoPrimerNodo;
        }
        size++;
    }

    @Override
    public void addLast(T value) {
        add(value);
    }

    public void addascendente(T value) {
        NodoDLL<T> nodoAAgregar = new NodoDLL<>(value);
        if (primerElemento == null) {
            addFisrt(value);
        } else {
            int valorDelNodo = (int) nodoAAgregar.getValue();//Valor del nodo que quiero agregar
            NodoDLL<T> nodoPrevioAAgregar = primerElemento;
            while (nodoPrevioAAgregar.getSiguiente() != null && (int) nodoPrevioAAgregar.getSiguiente().getValue() < valorDelNodo) {//Lo pongo en este orden pa no tener problema de comparacion con null, se corre de izquiera a derecha.
                nodoPrevioAAgregar = nodoPrevioAAgregar.getSiguiente();
            }
            if (nodoPrevioAAgregar == primerElemento && (int) nodoPrevioAAgregar.getValue() > valorDelNodo) {//Falto evaluarlo en el while
                addFisrt(value);
            } else {
                if (nodoPrevioAAgregar.getSiguiente() == null) {
                    addLast(value);
                } else {
                    nodoPrevioAAgregar.getSiguiente().setAnterior(nodoAAgregar);
                    nodoAAgregar.setSiguiente(nodoPrevioAAgregar.getSiguiente());
                    nodoPrevioAAgregar.setSiguiente(nodoAAgregar);
                    nodoAAgregar.setAnterior(nodoPrevioAAgregar);
                    size++;
                }
            }
        }
    }

    @Override
    public void addposition(T value, int position) {
        NodoDLL<T> nodoAAgregar = new NodoDLL<>(value);
        if (position < 0 || position > size) {
            System.out.println("La posicion en la que se quiere insertar el elemento es inaceptable");
        } else {
            if (position == 0) {
                addFisrt(value);
            } else {
                if (position == size) {
                    addLast(value);
                } else {
                    int i = 0;
                    NodoDLL<T> nodoAnterior = primerElemento;
                    while (i < position - 1) {
                        nodoAnterior = nodoAnterior.getSiguiente();
                        i++;
                    }
                    nodoAnterior.getSiguiente().setAnterior(nodoAAgregar);
                    nodoAAgregar.setSiguiente(nodoAnterior.getSiguiente());
                    nodoAnterior.setSiguiente(nodoAAgregar);
                    nodoAAgregar.setAnterior(nodoAnterior);
                    size++;
                }
            }
        }
    }

    public void intercanbiar(T elemento, int direccion){
        //primero identifico el elemento
        if (direccion!=1 && direccion!=-1){
            System.out.println("El campo de direccion debe ser -1 o 1");
        }else {
            NodoDLL<T> nodoBuscado = this.primerElemento;
            boolean encontro = false;
            for (int i = 0; i < this.getsize() ; i++) {
                if (this.get(i).equals(elemento)) {
                    encontro = true;
                    i = this.getsize() - 1;
                }else {
                    nodoBuscado = nodoBuscado.getSiguiente();
                }
            }
            //El nodo que quiero cambiar me lo encuentra perfecto
            if (!encontro) {
                System.out.println("El elemento pasado no coincide con nignuno de los que se encuentran en la lista");
            }else {
                if (direccion == 1) {//Lo cambio con el de adelante
                    if (nodoBuscado == this.ultimoElemento) {
                        System.out.println("No hay nodo para cambiarlo");
                    } else {
                        if (nodoBuscado == this.primerElemento) {
                            if(this.getsize()==2){
                                this.primerElemento = nodoBuscado.getSiguiente();
                                this.ultimoElemento = nodoBuscado;
                                nodoBuscado.setAnterior(nodoBuscado.getSiguiente());
                                nodoBuscado.getSiguiente().setAnterior(null);
                                nodoBuscado.getSiguiente().setSiguiente(nodoBuscado);
                                nodoBuscado.setSiguiente(null);
                            }else{
                                this.primerElemento = nodoBuscado.getSiguiente();
                                nodoBuscado.getSiguiente().setAnterior(nodoBuscado.getAnterior());
                                nodoBuscado.setSiguiente(nodoBuscado.getSiguiente().getSiguiente());
                                nodoBuscado.getSiguiente().getAnterior().setSiguiente(nodoBuscado);
                                nodoBuscado.setAnterior(nodoBuscado.getSiguiente().getAnterior());
                                nodoBuscado.getSiguiente().setAnterior(nodoBuscado);
                            }
                        } else {
                            if (nodoBuscado.getSiguiente() == this.ultimoElemento) {
                                this.ultimoElemento = nodoBuscado;
                                nodoBuscado.getSiguiente().setAnterior(nodoBuscado.getAnterior());
                                nodoBuscado.getAnterior().setSiguiente(nodoBuscado.getSiguiente());
                                nodoBuscado.getSiguiente().setSiguiente(nodoBuscado);
                                nodoBuscado.setAnterior(nodoBuscado.getSiguiente());
                                nodoBuscado.setSiguiente(null);
                            } else {
                                //Intercambio dos nodos del medio, UN HUEVO
                                nodoBuscado.getAnterior().setSiguiente(nodoBuscado.getSiguiente());
                                nodoBuscado.getSiguiente().setAnterior(nodoBuscado.getAnterior());
                                nodoBuscado.setSiguiente(nodoBuscado.getSiguiente().getSiguiente());
                                nodoBuscado.getSiguiente().getAnterior().setSiguiente(nodoBuscado);
                                nodoBuscado.setAnterior(nodoBuscado.getSiguiente().getAnterior());
                                nodoBuscado.getSiguiente().setAnterior(nodoBuscado);
                            }
                        }
                    }
                }else {//Direccion es igual a -1 y lo cambio con el de atras
                    if (nodoBuscado == this.primerElemento) {
                        System.out.println("No hay nodo para cambiarlo");
                    } else {
                        if (nodoBuscado == this.ultimoElemento) {
                            if(this.getsize()==2){
                                this.primerElemento = nodoBuscado;
                                this.ultimoElemento = nodoBuscado.getAnterior();
                                nodoBuscado.setSiguiente(nodoBuscado.getAnterior());
                                nodoBuscado.getAnterior().setAnterior(nodoBuscado);
                                nodoBuscado.getAnterior().setSiguiente(null);
                                nodoBuscado.setAnterior(null);
                            }else{
                                this.ultimoElemento = nodoBuscado.getAnterior();
                                nodoBuscado.getAnterior().setSiguiente(nodoBuscado.getSiguiente());
                                nodoBuscado.setAnterior(nodoBuscado.getAnterior().getAnterior());
                                nodoBuscado.getAnterior().getSiguiente().setAnterior(nodoBuscado);
                                nodoBuscado.setSiguiente(nodoBuscado.getAnterior().getSiguiente());
                                nodoBuscado.getAnterior().setSiguiente(nodoBuscado);
                            }
                        } else {
                            if (nodoBuscado.getAnterior() == this.primerElemento) {
                                this.primerElemento = nodoBuscado;
                                nodoBuscado.getSiguiente().setAnterior(nodoBuscado.getAnterior());
                                nodoBuscado.getAnterior().setSiguiente(nodoBuscado);
                                nodoBuscado.getAnterior().setSiguiente(nodoBuscado.getSiguiente());
                                nodoBuscado.setSiguiente(nodoBuscado.getAnterior());
                                nodoBuscado.setAnterior(null);
                            } else {
                                //Intercambio dos nodos del medio
                                nodoBuscado.getSiguiente().setAnterior(nodoBuscado.getAnterior());
                                nodoBuscado.getAnterior().setSiguiente(nodoBuscado.getSiguiente());
                                nodoBuscado.setAnterior(nodoBuscado.getAnterior().getAnterior());
                                nodoBuscado.getAnterior().getSiguiente().setAnterior(nodoBuscado);
                                nodoBuscado.setSiguiente(nodoBuscado.getAnterior().getSiguiente());
                                nodoBuscado.getAnterior().setSiguiente(nodoBuscado);
                            }
                        }
                    }
                }
            }
        }
    }


    public DobleListaEnlazada<T>[] comparacionListas(DobleListaEnlazada<T> listaAComparar) {
        DobleListaEnlazada<T>[] devolucion = (DobleListaEnlazada<T>[]) new Comparable[2];
        //Ya se de antemano cuantos elementos va a atener y quiero acceder por posicion
        DobleListaEnlazada<T> listaDeCoincidencia = new DobleListaEnlazada<>();
        DobleListaEnlazada<T> listaDeDiferencia = new DobleListaEnlazada<>();
        for (int i = 0; i < this.getsize(); i++) {
            if (listaAComparar.encontrarElemento(this.get(i))) {
                if (!listaDeCoincidencia.encontrarElemento(this.get(i)))//Controle que quede una sola vez cada elemento
                    listaDeCoincidencia.add(this.get(i));
            } else {
                if (!listaDeDiferencia.encontrarElemento(this.get(i))) //Controle que quede una sola vez cada elemento
                    listaDeDiferencia.add(this.get(i));
            }
        }//Como lo hago mejor!!!
        for (int i = 0; i < listaAComparar.getsize(); i++) {
            if (this.encontrarElemento(listaAComparar.get(i))) {
                if (!listaDeCoincidencia.encontrarElemento(listaAComparar.get(i)))//Controle que quede una sola vez cada elemento
                    listaDeCoincidencia.add(listaAComparar.get(i));
            } else {
                if (!listaDeDiferencia.encontrarElemento(listaAComparar.get(i))) //Controle que quede una sola vez cada elemento
                    listaDeDiferencia.add(listaAComparar.get(i));
            }
        }
        devolucion[0] = listaDeCoincidencia;
        devolucion[1] = listaDeDiferencia;
        return devolucion;
    }

}







