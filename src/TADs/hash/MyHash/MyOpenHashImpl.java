package TADs.hash.MyHash;

import TADs.hash.listaEnlazada.ListaEnlazada;

public class MyOpenHashImpl<K,V> implements MyHashTable<K,V> {

    private ListaEnlazada<K,V>[] hashTable;
    private final int DEFAULT_INITIAL_TABLE_HASH_SIZE = 10;
    private float size;
    private float loadFactor;

    // Cada entidad que quiero utilizar como clave debe implementar equals y hashcode


    public MyOpenHashImpl() {
        hashTable = new ListaEnlazada[DEFAULT_INITIAL_TABLE_HASH_SIZE];
        size = 0;
        this.loadFactor = 0.75f;
    }


    public MyOpenHashImpl(int tableHashSize, float loadFactor) {
        hashTable = new ListaEnlazada[tableHashSize];
        size = 0;
        this.loadFactor = loadFactor;
    }


    @Override
    public void put(K key, V value) {

        if (((size + 1) / (hashTable.length)) >= loadFactor) {
            this.hashTable = this.rehashing();
        }
        int posicionEnLaTabla = key.hashCode() % hashTable.length;
        if(hashTable[posicionEnLaTabla] == null){
            hashTable[posicionEnLaTabla] = new ListaEnlazada<>();
        }
        hashTable[posicionEnLaTabla].add(key, value);
        size++;

    }

    private ListaEnlazada<K,V>[] rehashing() {

        int nuevoTamanio = this.hashTable.length * 2;

        ListaEnlazada<K,V>[] devolucion = new ListaEnlazada[nuevoTamanio];

        ListaEnlazada<K,V>[] temp;
        temp = this.hashTable;
        this.hashTable = devolucion;
        this.size = 0; //Pongo en cero el size porque sino se me duplica cada vez que hago el rehashing
        for(int i = 0; i < (hashTable.length/2); i++){ // Este for me recorre la tabla de hash
            if(temp[i] != null){
                for(int j = 0; j < temp[i].getsize(); j++){
                    this.put(temp[i].getNodoPosicion(j).getKey(),temp[i].getNodoPosicion(j).getValue());
                }
            }
        }
        return devolucion;
    }

    @Override
    public V get(K key) {
        V devolucion = null;
        int posicionEnLaTabla = key.hashCode() % hashTable.length;
        devolucion = hashTable[posicionEnLaTabla].get(key);
        return devolucion;
    }

    @Override
    public boolean contains(K key) {
        boolean devolucion = false;
        int posicionEnLaTabla = key.hashCode() % hashTable.length;
        if(hashTable[posicionEnLaTabla] != null){
            devolucion = hashTable[posicionEnLaTabla].encontrarElemento(key);
        }
        return devolucion;
    }

    @Override
    public void remove(K key) {

        int posicionEnLaTabla = key.hashCode() % hashTable.length;
        hashTable[posicionEnLaTabla].remove(key);
        size--;

    }

    @Override
    public float getSize() {
        return (this.size);
    }

    public void mostrarHashAbierto(){

        for (int i = 0; i < hashTable.length; i++){
            if(hashTable[i] != null){
                hashTable[i].visualizar();
            }
            System.out.println(); // Paso al proximo bucket
        }
    }

    /*public static void main(String[] args)  {

        MyOpenHashImpl<Integer,Integer> prueba1 = new MyOpenHashImpl<>(10,0.9f);

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
        prueba1.mostrarHashAbierto();
        prueba1.put(11,11);
        prueba1.put(222,222);
        prueba1.put(12,12);
        prueba1.put(34,34);
        prueba1.put(55,55);
        prueba1.put(68,68);
        prueba1.put(72,72);
        prueba1.put(97,97);
        prueba1.put(95,95);
        prueba1.mostrarHashAbierto();

        System.out.println("Aca empiezan las pruebas del contains");

        if(prueba1.contains(4)){
            System.out.println("El contains parace funcionar BIEN");
        }else{
            System.out.println("Parece haber un ERROR con el contains");
        }
        if(prueba1.contains(12)){
            System.out.println("El contains parace funcionar BIEN");
        }else{
            System.out.println("Parece haber un ERROR con el contains");
        }
        if(prueba1.contains(1)){
            System.out.println("El contains parace funcionar BIEN");
        }else{
            System.out.println("Parece haber un ERROR con el contains");
        }
        if(!(prueba1.contains(0))){
            System.out.println("El contains parace funcionar BIEN");
        }else{
            System.out.println("Parece haber un ERROR con el contains");
        }

        System.out.println("Aca empiezan las pruebas del size");

        if(prueba1.getSize() == 17){
            System.out.println("El size parace funcionar BIEN");
        }else{
            System.out.println("Parece haber un ERROR con el size");
            System.out.println("El size se esta marcando como : " + prueba1.getSize());
        }

        System.out.println("Pruebas generales con remove: ");

        MyOpenHashImpl<Integer,Integer> prueba2 = new MyOpenHashImpl(10,0.8f);
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
        prueba2.mostrarHashAbierto();
        if(prueba2.getSize() == 7){
            System.out.println("El put parece funcionar BIEN");
        }else{
            System.out.println("Parece haber un PROBLEMA con el put. Estaria seindo : " + prueba2.getSize());
        }
        System.out.println();
        prueba2.remove(7);
        prueba2.mostrarHashAbierto();
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

        prueba2.mostrarHashAbierto();
        prueba2.remove(2);
        prueba2.remove(12);
        prueba2.remove(5);
        prueba2.remove(17);
        prueba2.remove(77);

        if(prueba2.getSize() == 0){
            System.out.println("El size parece estar funcionando BIEN");
        }else{
            System.out.println("Parece estar habiendo un PROBLEMA en el remove o size pues el tamaños es " + prueba2.getSize());
        }
        prueba2.mostrarHashAbierto();



    }*/
}
