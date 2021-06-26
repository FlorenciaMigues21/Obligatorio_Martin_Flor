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


}

