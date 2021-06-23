package TADs.hash.MyHash;

import static java.lang.Math.abs;

public class MyClosedHashImpl<K,V> implements MyHashTable<K,V>{

    private ClosedHashNode[] hashTable;
    private final int DEFAULT_INITIAL_TABLE_HASH_SIZE = 10;
    private float size;
    private float loadFactor;

    // Cada entidad que quiero utilizar como clave debe implementar equals y hashcode


    public MyClosedHashImpl(){
        hashTable = new ClosedHashNode[DEFAULT_INITIAL_TABLE_HASH_SIZE];
        size = 0;
        this.loadFactor = 0.75f;
    }


    public MyClosedHashImpl(int tableHashSize, float loadFactor){
        hashTable = new ClosedHashNode[tableHashSize];
        size = 0;
        this.loadFactor = loadFactor;
    }


    @Override
    public void put(K key, V value) {

        if(((size + 1) / (hashTable.length)) >= loadFactor){
            this.hashTable = this.rehashing();
        }
        ClosedHashNode<K,V> nodoAAgregar = new ClosedHashNode<>(key, value);
        int posicionEnLaTabla = abs(key.hashCode() % hashTable.length);
        int iteraciones = 0;
        while(iteraciones <= hashTable.length){ // Controlo no tener la tabla de hash llena, que nunca deberia pasar
            if(hashTable[posicionEnLaTabla] == null || hashTable[posicionEnLaTabla].isdeleted()){
                hashTable[posicionEnLaTabla] = nodoAAgregar;
                size++;
                break;
            }else if(hashTable[posicionEnLaTabla].getKey().equals(key)){
                hashTable[posicionEnLaTabla] = nodoAAgregar; // Piso el valor viejo
                break;
            }else{
                if(posicionEnLaTabla == hashTable.length - 1){
                    posicionEnLaTabla = 0;
                }else{
                    posicionEnLaTabla++;
                }
            }
            iteraciones++;
        }
    }



    private boolean numeroEsPrimo(int numero){
        boolean devolucion = true;
        for(int i = 2; i < numero; i++){
            if(i % numero == 0){
                devolucion = false;
                break;
            }
        }
        return devolucion;
    }


    private ClosedHashNode[] rehashing() {
        int nuevoTamaño = this.hashTable.length * 2;
        int newSizePorArriba = nuevoTamaño;
        int newSizePorAbajo = nuevoTamaño;
        // Busco el primo mas cercano por arriba
        while(true){
            if(numeroEsPrimo(newSizePorArriba)){
                break;
            }
            newSizePorArriba++;
        }
        // Busco el primo mas cercano por ajoba
        while(true){
            if(numeroEsPrimo(newSizePorAbajo)){
                break;
            }
            newSizePorAbajo--;
        }
        if((nuevoTamaño - newSizePorArriba) > (nuevoTamaño - newSizePorAbajo)){
            nuevoTamaño = newSizePorAbajo;
        }else{
            nuevoTamaño = newSizePorArriba;
        }


        ClosedHashNode[] devolucion = new ClosedHashNode[nuevoTamaño];
        ClosedHashNode[] temp;
        temp = this.hashTable;
        this.hashTable = devolucion;
        this.size = 0; //Pongo en cero el size porque sino se me duplica cada vez que hago el rehashing
        for(int i = 0; i < (hashTable.length/2); i++){
            if(temp[i] != null && !(temp[i].isdeleted())){
                this.put((K) temp[i].getKey(),(V) temp[i].getValue());
            }
        }
        //System.out.println("PROBLEMA");
        return devolucion;
    }

    @Override
    public V get(K key) {
        V devolucion = null;
        int posicionEnLaTabla = abs(key.hashCode() % hashTable.length);
        int iteraciones = 0;
        while(iteraciones <= hashTable.length){ // Controlo no haber buscado en toda la tabla y no haber encontrado
            if(hashTable[posicionEnLaTabla] != null){
                if(hashTable[posicionEnLaTabla].getKey().equals(key) && !(hashTable[posicionEnLaTabla].isdeleted())){
                    devolucion = (V) hashTable[posicionEnLaTabla].getValue(); // Esta bien castearlo? Creemos que si porque nos aseguramos que no sea null
                    break;
                }else{
                    if(posicionEnLaTabla == hashTable.length - 1){
                        posicionEnLaTabla = 0;
                    }else{
                        posicionEnLaTabla++;
                    }
                }
                iteraciones++;
            }else{
                break;
            }
        }
        return devolucion;
    }

    @Override
    public boolean contains(K key) {
        boolean devolucion = false;
        int posicionEnLaTabla = abs(key.hashCode() % hashTable.length);
        int iteraciones = 0;
        while(iteraciones <= hashTable.length){
            if(hashTable[posicionEnLaTabla] != null){ // Implica que si tengo un IsDeleted va a seguir buscando
                if(hashTable[posicionEnLaTabla].getKey().equals(key) && !(hashTable[posicionEnLaTabla].isdeleted())){
                    devolucion = true;
                    break;
                }else{
                    if(posicionEnLaTabla == hashTable.length - 1){
                        posicionEnLaTabla = 0;
                    }else{
                        posicionEnLaTabla++;
                    }
                }
                iteraciones++;
            }else{
                break;
            }
        }
        return devolucion;
    }

    @Override
    public void remove(K key) {
        boolean seBorro = false;
        int posicionEnLaTabla = abs(key.hashCode() % hashTable.length);
        int iteraciones = 0;
        while(iteraciones <= hashTable.length){ // Controlo no haber buscado en toda la tabla y no haber encontrado
            if(hashTable[posicionEnLaTabla] != null){
                if(hashTable[posicionEnLaTabla].getKey().equals(key) && !(hashTable[posicionEnLaTabla].isdeleted())){
                    hashTable[posicionEnLaTabla].setIsdeleted(true); // Es necesario borrar el contenido de adentro? O lo hacemos como lo venimos implementando solo deteando isdeleted?
                    size--;
                    seBorro = true;
                    break;
                }else{
                    if(posicionEnLaTabla == hashTable.length - 1){
                        posicionEnLaTabla = 0;
                    }else{
                        posicionEnLaTabla++;
                    }
                }
                iteraciones++;
            }else{ // No existe lo que me mandaron borrar
                break;
            }
        }
    }

    @Override
    public float getSize() {
        return (this.size);
    }

    private void mostrarHashCerrado(){
        for (int i = 0; i < hashTable.length; i++){
            if(hashTable[i] ==  null || hashTable[i].isdeleted()){
                System.out.println();
            }else{
                System.out.println(hashTable[i].getValue().toString());
            }
        }
    }

    public static void main(String[] args) throws InvalidInformation {

        MyClosedHashImpl<Integer,Integer> prueba1 = new MyClosedHashImpl(10,0.9f);

        System.out.println("Aca empiezan las pruebas del put");
        prueba1.put(1,1);
        prueba1.put(2,2);
        prueba1.put(3,3);
        prueba1.put(4,4);
        prueba1.put(5,5);
        prueba1.put(6,6);
        prueba1.put(7,7);
        prueba1.put(9,9);
        prueba1.put(9,9); // No debe dejarme agregarlo
        prueba1.mostrarHashCerrado();
        prueba1.put(11,11);
        prueba1.put(222,222);
        prueba1.put(12,12);
        prueba1.put(34,34);
        prueba1.put(55,55);
        prueba1.put(68,68);
        prueba1.put(72,72);
        prueba1.put(97,97);
        prueba1.put(95,95);
        prueba1.mostrarHashCerrado();

        System.out.println("Aca empiezan las pruebas del contains");

        if(prueba1.contains(72)){
            System.out.println("El contains parace funcionar bien");
        }else{
            System.out.println("Parece haber un error con el contains");
        }
        if(prueba1.contains(12)){
            System.out.println("El contains parace funcionar bien");
        }else{
            System.out.println("Parece haber un error con el contains");
        }
        if(prueba1.contains(1)){
            System.out.println("El contains parace funcionar bien");
        }else{
            System.out.println("Parece haber un error con el contains");
        }
        if(!(prueba1.contains(2789))){
            System.out.println("El contains parace funcionar bien");
        }else{
            System.out.println("Parece haber un error con el contains");
        }

        System.out.println("Aca empiezan las pruebas del size");

        if(prueba1.getSize() == 17){
            System.out.println("El size parace funcionar bien");
        }else{
            System.out.println("Parece haber un error con el size");
            System.out.println("El size se esta marcando como : " + (int) prueba1.getSize());
        }

        System.out.println("Pruebas generales con remove: ");

        MyClosedHashImpl<Integer,Integer> prueba2 = new MyClosedHashImpl(10,0.8f);
        prueba2.put(2,2);
        prueba2.put(12,12);
        prueba2.put(4,4);
        prueba2.put(5,5);
        prueba2.put(7,7);
        prueba2.put(4,4); // Me deberia imprimir que no me lo puede agregar porque ya tengo otro elemento
        prueba2.put(5,5);  //Me deberia imprimir que no me lo puede agregar porque ya tengo otro elemento
        prueba2.put(7,7); // Me deberia imprimir que no me lo puede agregar porque ya tengo otro elemento
        prueba2.put(17,17);
        prueba2.put(77,77);
        prueba2.mostrarHashCerrado();
        if(prueba2.getSize() == 7){
            System.out.println("El put parece funcionar bien");
        }else{
            System.out.println("Parece haber un problema con el put. Estaria seindo : " + prueba2.getSize());
        }
        System.out.println();
        prueba2.remove(7);
        prueba2.mostrarHashCerrado();
        if(prueba2.getSize() == 6){
            System.out.println("El put parece funcionar BIEN");
        }else{
            System.out.println("Parece haber un PROBLEMA con el put. Estaria seindo : " + prueba2.getSize());
        }
        if(prueba2.contains(17)){
            System.out.println("El contains parece funcionar BIEN");
        }else{
            System.out.println("Parece haber un PROBLEMA con el contains");
        }

        if (prueba2.get(77) == 77){
            System.out.println("El get parece estar funcionando BIEN");
        }else{
            System.out.println("Parece estar habiendo un PROBLEMA en el get");
        }

        prueba2.remove(4);

        prueba2.mostrarHashCerrado();
        prueba2.remove(2);
        prueba2.remove(12);
        //prueba2.remove(4);
        prueba2.remove(5);
        prueba2.remove(17);
        prueba2.remove(77);

        if(prueba2.getSize() == 0){
            System.out.println("El size parece estar funcionando BIEN");
        }else{
            System.out.println("Parece estar habiendo un PROBLEMA en el remove o size pues el tamaños es " + prueba2.getSize());
        }
        prueba2.mostrarHashCerrado();


    }
}
