package TADs.queue;

public class QueueConPrioridad<T> implements MyPriorityQueue<T> {


    public NodoPrioridad<T> primerElemento = null;
    public NodoPrioridad<T> ultimoElemento = null;
    public int size = 0;

    @Override
    public int getSize() {
        return this.size;
    }


    public void add(T value, int prioridad) {
        NodoPrioridad<T> nodoAAgregar = new NodoPrioridad<T>(value, prioridad);
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

    public NodoPrioridad<T> get(int position) {
        NodoPrioridad<T> devolucion;
        if (position > size - 1 || position < 0) {
            System.out.println("La posicion no existe en este vector");
            devolucion = null;
        } else {
            int contador = 0;
            NodoPrioridad<T> nodoBuscado = primerElemento;
            //En la posicion n-1 entro al while, lo cambio y cuando estoy con el contador en n pregunto para entrar y me dice que no pero ya tengo al nodo buscado como el que esta en la posicion n
            while (contador < position) {
                nodoBuscado = nodoBuscado.getSiguiente();
                contador++;
            }
            devolucion = nodoBuscado;
        }
        return devolucion;
    }

    public boolean encontrarElemento(T elemento) {
        NodoPrioridad<T> temp = primerElemento;
        for (int i = 0; i < size - 1; i++) {
            if (temp.getValue().equals(elemento)) {
                return true;
            }
            temp = temp.getSiguiente();
        }
        return false;
    }

    public void addFisrt(T value, int prioridad) {
        NodoPrioridad<T> nuevoPrimerNodo = new NodoPrioridad<>(value, prioridad);
        if (primerElemento == null) {
            primerElemento = nuevoPrimerNodo;
            ultimoElemento = nuevoPrimerNodo;
        } else {
            NodoPrioridad<T> nuevoSegundoNodo = primerElemento;
            primerElemento = nuevoPrimerNodo;
            nuevoPrimerNodo.setSiguiente(nuevoSegundoNodo);
        }
        size++;
    }

    public void addLast(T value, int prioridad) {
        this.add(value, prioridad);
    }

    public void addposition(T value, int position, int prioridad) {
        NodoPrioridad<T> nodoAAgregar = new NodoPrioridad<>(value, prioridad);
        if (position < 0 || position > size) {
            System.out.println("La posicion en la que se quiere insertar el elemento es inaceptable. yndes aut of baundz");
        } else {
            if (position == 0) {
                addFisrt(value, prioridad);
            } else {
                if (position == size) {
                    addLast(value, prioridad);
                } else {
                    int i = 0;
                    NodoPrioridad<T> nodoAnterior = primerElemento;
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


    @Override
    public void enqueueWithPriority(T element, int prioridad) { //NO ESTOY ACTUALIZANDO ULTIMO ELEMENTO
        //Recorro la lista desde el final al principio para encontrar donde corresponde que entre el elemento que quiero meter
        boolean condicion = true;
        NodoPrioridad<T> nodoAAgregar = new NodoPrioridad<>(element, prioridad);
        NodoPrioridad<T> temp = primerElemento; // Voy recorriendo en el orden contraintuitivo porque es la lista enlazada
        if(this.getSize() == 0){
            this.addFisrt(element, prioridad);
        }else{
            while (condicion) {
                if (temp.getSiguiente() == null || temp.getSiguiente().getPrioridad() < prioridad) {
                    condicion = false;
                    break;
                }
                temp = temp.getSiguiente();
            }
            //Hay un caso que no lo evalua que es la comparacion con el primer nodo, qe seria cuando quiero agregar un elemento con mas improtancia quetodo e resto
            if(temp == primerElemento){
                if(temp.getPrioridad() < prioridad){
                    this.addFisrt(element, prioridad);
                }else{
                    nodoAAgregar.setSiguiente(temp.getSiguiente());
                    temp.setSiguiente(nodoAAgregar);
                    size++;
                }
            }else{
                nodoAAgregar.setSiguiente(temp.getSiguiente());
                temp.setSiguiente(nodoAAgregar);
                size++;
            }
        }
    }

    public void enqueueWithPriority2(T value, int priority){ // EN ESTA OPERACION LOS ELEMENTOS ENTRAN POR LA IZQUIERDA Y SALEN POR LA DERECHA
        NodoPrioridad<T> nodoAAgregar = new NodoPrioridad<>(value, priority);
        //PENSAMOS QUE EL QUE TIENE MAYOR PRIORIDAD SALE PRIMERO
        if(primerElemento == null){
            primerElemento = nodoAAgregar;
            ultimoElemento = nodoAAgregar;
        }else if (primerElemento.getSiguiente()==null || primerElemento.getPrioridad() > priority){
            if(primerElemento.getPrioridad() <= priority){
                primerElemento.setSiguiente(nodoAAgregar);
                ultimoElemento = nodoAAgregar;
            }else{
                nodoAAgregar.setSiguiente(primerElemento);
                primerElemento = nodoAAgregar;
            }
        }else{
            NodoPrioridad<T> temp = this.primerElemento;
            while(temp.getSiguiente()!=null && priority > temp.getSiguiente().getPrioridad()){ //Voy avanzando a la derecha mientras que tenga mayor prioridad
                temp = temp.getSiguiente();
            }

            if (temp.getSiguiente() == null){
                ultimoElemento = nodoAAgregar;
            }
            nodoAAgregar.setSiguiente(temp.getSiguiente());
            temp.setSiguiente(nodoAAgregar);
            //SI SALGO POR LA CONDICION temp.getSiguiente()!=null. Estoy parado en el ultimo elemento


        }
    }


    @Override
    public void enqueue(T element) {
        enqueueWithPriority(element,0);//Paso con prioridad cero por defecto
    }

    @Override
    public T dequeue() throws EmptyQueueException {
        if(this.getSize()==0){
            throw new EmptyQueueException();
        }
        T devolucion = this.get(0).getValue();
        if (size > 1) {
            primerElemento = primerElemento.getSiguiente();
        } else {
            primerElemento = null;
            ultimoElemento = null;
        }
        size--;
        return devolucion;
    }

    @Override
    public boolean isEmpty() {
        return (this.getSize() == 0);
    }


    public static void main(String[] args) {
        MyPriorityQueue<Integer> prueba =new QueueConPrioridad<>();
        prueba.enqueueWithPriority(2,0);
        prueba.enqueueWithPriority(6,1);
        prueba.enqueueWithPriority(4,1);
        prueba.enqueueWithPriority(5,1);
        prueba.enqueueWithPriority(7,8);
        prueba.enqueueWithPriority(10,288);
        prueba.enqueue(888);
        prueba.enqueueWithPriority(56,11);
        prueba.enqueueWithPriority(99,8);
        prueba.enqueueWithPriority(74,2);
        prueba.enqueueWithPriority(11,5);
        prueba.enqueueWithPriority(70,9);
        prueba.enqueueWithPriority(12,77);
        prueba.enqueueWithPriority(21,10);
        prueba.enqueueWithPriority(3,6);
        prueba.enqueueWithPriority(9,9);
        prueba.enqueueWithPriority(17,1);
        prueba.enqueueWithPriority(19,0);
        prueba.enqueueWithPriority(14,0);
        System.out.println("El size es " + prueba.getSize());
        for (int i=prueba.getSize();i>0;i--){ //DEBE IMPRIMIR 10 12 56 21 70 9 7 99 3 11 74 6 4 5 17 2 888 19 14
            try {
                System.out.println(prueba.dequeue());
            }catch(EmptyQueueException e){
                System.out.println("La prueba salio mal");
            }
        }
        System.out.println("----------------------------");
        try{
            prueba.dequeue();
        }catch (EmptyQueueException e){
            System.out.println("La prueba dos esta bien");
        }
        System.out.println("----------------------------");
        prueba.enqueue(2);
        prueba.enqueue(3);
        prueba.enqueue(4);
        prueba.enqueue(5);
        prueba.enqueueWithPriority(0,1);//0 2 3 4 5
        for (int i=prueba.getSize();i > 0;i--){
            try {
                System.out.println(prueba.dequeue());
            }catch(EmptyQueueException e){
                System.out.println("La prueba salio mal");
            }
        }
    }
}