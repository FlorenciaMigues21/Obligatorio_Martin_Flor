
import TADs.arraylist.ArrayList;
import TADs.arraylist.ListaArray;
import TADs.hash.MyHash.MyClosedHashImpl;
import TADs.hash.MyHash.MyHashTable;
import TADs.heap.MyHeap;
import TADs.heap.MyHeapImpl;
import TADs.listaEnlazada.Lista;
import entities.CastMember;
import entities.CauseOfDeath;
import entities.Movie;
import entities.MovieCastMember;

import javax.xml.catalog.Catalog;
import java.awt.event.FocusEvent;

public class Consultas {


    private ListaArray<Float> listaPromedios = new ArrayList<>(14);
    private ListaArray<Movie> listaPeliMasWA = new ArrayList<>(14);

    // Constructor
    public Consultas() {
    }

    //Consulta 1
    public void consulta1(ListaArray<MovieCastMember> listaActores, MyHashTable<String, CastMember> hashCast) {
        long firstTime = System.nanoTime();
        ListaArray<CastMember> listaTop5 = new ArrayList<>(5);
        for (int j = 0; j < 5; j++) { // No se contempla que entre los perimero 5 castmembers este el mismo ya que es un caso irreale (para eso necesito, al menos, una pelicula de integrantes en total)
            listaTop5.addLast(hashCast.get(listaActores.get(j).getImdbNameId()));
        }
        int aparicionesminimas = 1;
        CastMember temp;
        for (int i = 5; i < listaActores.size(); i++) {
            temp = hashCast.get(listaActores.get(i).getImdbNameId());
            if (listaTop5.find(temp) != -1) {
                // Va ser el mismo actor que ya tenia
            } else if (temp.getMovieCastMemberActor().size() > aparicionesminimas) { // Este cast member entra al vector y tengo que reoganizarlo
                aparicionesminimas = this.ordenarYMinima(listaTop5, temp);
            }
        }
        // Imprimo en pantalla lo correspondiente a la consulta 1
        long lastTime = System.nanoTime();
        long dif2 = lastTime - firstTime;
        double timeTotal = (double) dif2/1000000000;
        for (int k = 0; k < 5; k++) {
            System.out.println("\nNombre actor/actriz: " + listaTop5.get(k).getName() + "\nCantidad de apariciones : " + listaTop5.get(k).getMovieCastMemberActor().size() + "\n");
        }
        System.out.println( "\nTiempo deejecuciónde la consulta:" + "\n" + timeTotal);
    }

    public int ordenarYMinima(ListaArray<CastMember> lista, CastMember castAgregar){
        lista.addLast(castAgregar);
        boolean estaOrdenado = false;
        while (!estaOrdenado) {
            estaOrdenado = true;
            for (int i = 5; i > 0; i--) {
                if (lista.get(i).getMovieCastMemberActor().size() > lista.get(i - 1).getMovieCastMemberActor().size()) {
                    CastMember temp = lista.get(i);
                    lista.addPisando(lista.get(i - 1), i);
                    lista.addPisando(temp, i - 1);
                    estaOrdenado = false;
                }
            }
        }
        lista.remove(5);// Saco el elemento minimo
        return lista.get(4).getMovieCastMemberActor().size();
    }


    // Consulta 2

    public void consulta2(ListaArray<MovieCastMember> listaProdDir, MyHashTable<String, CastMember> hashCast){

        long firstTime = System.nanoTime();
        ListaArray<CauseOfDeath> listaTop5Death = new ArrayList<>(5);
        MyHashTable<String,Integer> HashCantCausas = new MyClosedHashImpl<>(2000,1f);
        MyHashTable<String,CastMember> HashProdDir = new MyClosedHashImpl<>(500,1f);

        for(int i = 0; i < listaProdDir.size(); i++){ //  Recorro la lista de productores y directores
            CastMember temp = hashCast.get(listaProdDir.get(i).getImdbNameId());
                if (!HashProdDir.contains(temp.getImdbNameId()) && temp.getCausasDeMuerte() != null && (temp.getBirthCountry().contains("UK") || temp.getBirthCountry().contains("USA") || temp.getBirthCountry().contains("France") || temp.getBirthCountry().contains("Italy"))) {
                    HashProdDir.put(temp.getImdbNameId(), temp);
                    Integer causa = HashCantCausas.get(temp.getCausasDeMuerte().getName());
                    if (causa != null) {
                        causa++;
                        HashCantCausas.put(temp.getCausasDeMuerte().getName(), causa);//SET SE LE SUMA
                        causa = HashCantCausas.get(temp.getCausasDeMuerte().getName());
                    } else {
                        HashCantCausas.put(temp.getCausasDeMuerte().getName(), 1);
                        causa = HashCantCausas.get(temp.getCausasDeMuerte().getName());
                    }
                    if (listaTop5Death.size() < 5) {
                        listaTop5Death.addLast(temp.getCausasDeMuerte());
                    }else {
                        int pos = listaTop5Death.find(temp.getCausasDeMuerte());
                        if (pos != -1) {
                            this.acomodarMuertes(pos, HashCantCausas, listaTop5Death);
                        } else if (causa > HashCantCausas.get(listaTop5Death.get(4).getName())) {
                            listaTop5Death.addPisando(temp.getCausasDeMuerte(), 4);
                            pos = 4;
                            this.acomodarMuertes(pos,HashCantCausas, listaTop5Death);
                        }
                    }
                }
        }
        long lastTime = System.nanoTime();
        long dif2 = lastTime - firstTime;
        double timeTotal = (double) dif2/1000000000;
        // Imprimo en pantalla lo correspondiente a la consulta 2
        for (int k = 0; k < 5; k++) {
            System.out.println("\nCausa de muerte: " + listaTop5Death.get(k).getName() + "\nCantidad de personas: " + HashCantCausas.get(listaTop5Death.get(k).getName()) + "\n");
        }
        System.out.println( "\nTiempo deejecuciónde la consulta:" + "\n" + timeTotal);
    }




    // Consulta 3
    public void consulta3(ListaArray<ListaArray<Movie>> listaMoviesAños, MyHashTable<String,CastMember> hashCastMmeber) {

        ListaArray<Movie> temp = listaMoviesAños.get(4);
        MyHeap<Float, Movie> heap = this.heapSort(temp);
        while (this.listaPeliMasWA.size() < 14) {
            Movie peliculaAgregar = heap.delete();
            if (peliculaAgregar.getYear() <= 1960) {
                this.listaPeliMasWA.addLast(peliculaAgregar);
            }
        }
        // En este punto ya tengo mi lista de lista de 14 peliculas
        for (int i = 0; i < 14; i++) {
            Movie temp3 = this.listaPeliMasWA.get(i);
            float promedio = 0;
            int divisor = 0;
            for (int j = 0; j < temp3.getMovieCastMemberProdDire().size(); j++) {
                MovieCastMember mcm = temp3.getMovieCastMemberProdDire().get(j);
                if (mcm.getCategory().toLowerCase().contains("actor") || mcm.getCategory().toLowerCase().contains("actress")) {
                    int temp4 = hashCastMmeber.get(mcm.getImdbNameId()).getHeight();
                    promedio = promedio + temp4;
                    if (temp4 != 0) {
                        divisor++;
                    }
                }
            }
            this.listaPromedios.addLast(promedio / divisor);
        }
        for (int i = 0; i < 14; i++) {
            if (!this.listaPromedios.get(i).isNaN()) {
                System.out.println("\nId película: " + this.listaPeliMasWA.get(i).getImdbTitleld() + "\nNombre: " + this.listaPeliMasWA.get(i).getTitle() + "\nAltura promedio de actores: " + listaPromedios.get(i));
            }
        }
    }




    public MyHeap<Float, Movie> heapSort(ListaArray<Movie> lista) {

        MyHeap<Float, Movie> heap = new MyHeapImpl<>(lista.size(), true); // Ingreso en un heap maximo
        for(int i = 0; i < lista.size(); i++){
            heap.insert(lista.get(i).getMovieRating().getWeightedAverage(), lista.get(i));
        }
        return heap;
    }

   /* public void heapSort(T[] arrayToOrder, boolean arribaMax){
        int heapLenght = 1;
        if (arribaMax){
            int posicion;
            for (int i = 0; i < arrayToOrder.length-1; i++){
                posicion = heapLenght;
                while (posicion != 0 && arrayToOrder[posicion].compareTo(arrayToOrder[obtenerPosicionPadre(posicion)]) < 0){
                    T temp = arrayToOrder[posicion];
                    arrayToOrder[posicion] = arrayToOrder[obtenerPosicionPadre(posicion)];
                    arrayToOrder[obtenerPosicionPadre(posicion)] = temp;
                    posicion = obtenerPosicionPadre(posicion);
                }
                heapLenght++;
            }
            for (int i = 0; i < arrayToOrder.length; i++){
                T agregarAlFinal = arrayToOrder[0];
                arrayToOrder[0] = arrayToOrder[heapLenght-1];
                boolean noEstaOrdenado = true;
                posicion = 0;
                while (noEstaOrdenado) {
                    if (obtenerPosicionHijoIzquierdo(posicion) >= heapLenght && obtenerPosicionHijoDerecho(posicion) >= heapLenght){
                        noEstaOrdenado = false;
                    }
                    else if (obtenerPosicionHijoDerecho(posicion) < heapLenght && obtenerPosicionHijoIzquierdo(posicion) < heapLenght){
                        int posicionMenor;
                        if (arrayToOrder[obtenerPosicionHijoDerecho(posicion)].compareTo(arrayToOrder[obtenerPosicionHijoIzquierdo(posicion)]) < 0){
                            posicionMenor = obtenerPosicionHijoDerecho(posicion);
                        }
                        else {
                            posicionMenor = obtenerPosicionHijoIzquierdo(posicion);
                        }
                        if (arrayToOrder[posicion].compareTo(arrayToOrder[posicionMenor]) > 0){
                            T temp = arrayToOrder[posicion];
                            arrayToOrder[posicion] = arrayToOrder[posicionMenor];
                            arrayToOrder[posicionMenor] = temp;
                            posicion = posicionMenor;
                        }
                        else {
                            noEstaOrdenado = false;
                        }
                    }
                    else if (obtenerPosicionHijoIzquierdo(posicion) < heapLenght){
                        if (arrayToOrder[posicion].compareTo(arrayToOrder[obtenerPosicionHijoIzquierdo(posicion)]) > 0){
                            T temp = arrayToOrder[posicion];
                            arrayToOrder[posicion] = arrayToOrder[obtenerPosicionHijoIzquierdo(posicion)];
                            arrayToOrder[obtenerPosicionHijoIzquierdo(posicion)] = temp;
                        }
                        noEstaOrdenado = false;
                    }
                    else {
                        if (arrayToOrder[posicion].compareTo(arrayToOrder[obtenerPosicionHijoDerecho(posicion)]) > 0) {
                            T temp = arrayToOrder[posicion];
                            arrayToOrder[posicion] = arrayToOrder[obtenerPosicionHijoDerecho(posicion)];
                            arrayToOrder[obtenerPosicionHijoDerecho(posicion)] = temp;
                        }
                        noEstaOrdenado = false;
                    }
                }
                arrayToOrder[heapLenght-1] = agregarAlFinal;
                heapLenght--;
            }
        }
        else {
            int posicion;
            for (int i = 0; i < arrayToOrder.length - 1; i++){
                posicion = heapLenght;
                while (posicion != 0 && arrayToOrder[posicion].compareTo(arrayToOrder[obtenerPosicionPadre(posicion)]) > 0){
                    T temp = arrayToOrder[posicion];
                    arrayToOrder[posicion] = arrayToOrder[obtenerPosicionPadre(posicion)];
                    arrayToOrder[obtenerPosicionPadre(posicion)] = temp;
                    posicion = obtenerPosicionPadre(posicion);
                }
                heapLenght++;
            }
            for (int i = 0; i < arrayToOrder.length; i++){
                T agregarAlFinal = arrayToOrder[0];
                arrayToOrder[0] = arrayToOrder[heapLenght-1];
                boolean noEstaOrdenado = true;
                posicion = 0;
                while (noEstaOrdenado) {
                    if (obtenerPosicionHijoIzquierdo(posicion) >= heapLenght && obtenerPosicionHijoDerecho(posicion) >= heapLenght){
                        noEstaOrdenado = false;
                    }
                    else if (obtenerPosicionHijoDerecho(posicion) < heapLenght && obtenerPosicionHijoIzquierdo(posicion) < heapLenght){
                        int posicionMayor;
                        if (arrayToOrder[obtenerPosicionHijoDerecho(posicion)].compareTo(arrayToOrder[obtenerPosicionHijoIzquierdo(posicion)]) > 0){
                            posicionMayor = obtenerPosicionHijoDerecho(posicion);
                        }
                        else {
                            posicionMayor = obtenerPosicionHijoIzquierdo(posicion);
                        }
                        if (arrayToOrder[posicion].compareTo(arrayToOrder[posicionMayor]) < 0){
                            T temp = arrayToOrder[posicion];
                            arrayToOrder[posicion] = arrayToOrder[posicionMayor];
                            arrayToOrder[posicionMayor] = temp;
                            posicion = posicionMayor;
                        }
                        else {
                            noEstaOrdenado = false;
                        }
                    }
                    else if (obtenerPosicionHijoIzquierdo(posicion) < heapLenght){
                        if (arrayToOrder[posicion].compareTo(arrayToOrder[obtenerPosicionHijoIzquierdo(posicion)]) < 0){
                            T temp = arrayToOrder[posicion];
                            arrayToOrder[posicion] = arrayToOrder[obtenerPosicionHijoIzquierdo(posicion)];
                            arrayToOrder[obtenerPosicionHijoIzquierdo(posicion)] = temp;
                        }
                        noEstaOrdenado = false;
                    }
                    else {
                        if (arrayToOrder[posicion].compareTo(arrayToOrder[obtenerPosicionHijoDerecho(posicion)]) < 0) {
                            T temp = arrayToOrder[posicion];
                            arrayToOrder[posicion] = arrayToOrder[obtenerPosicionHijoDerecho(posicion)];
                            arrayToOrder[obtenerPosicionHijoDerecho(posicion)] = temp;
                        }
                        noEstaOrdenado = false;
                    }
                }
                arrayToOrder[heapLenght-1] = agregarAlFinal;
                heapLenght--;
            }
        }
    }*/

    public void acomodarMuertes(int pos, MyHashTable<String, Integer> hashCantCausa, ListaArray<CauseOfDeath> listaTop5Death){
        while (pos > 0) {
            if (hashCantCausa.get(listaTop5Death.get(pos).getName()) > hashCantCausa.get(listaTop5Death.get(pos-1).getName())) {
                // Hago el cambio
                listaTop5Death.intercambiarMedio(pos);
                pos--;
            }else{
                break;
            }
        }
    }
}










