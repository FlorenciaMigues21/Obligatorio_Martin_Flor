
import TADs.arraylist.ArrayList;
import TADs.arraylist.ListaArray;
import TADs.hash.MyHash.MyClosedHashImpl;
import TADs.hash.MyHash.MyHashTable;
import TADs.heap.MyHeap;
import TADs.heap.MyHeapImpl;
import entities.CastMember;
import entities.CauseOfDeath;
import entities.Movie;
import entities.MovieCastMember;

public class Consultas {


    // Constructor
    public Consultas() {
    }

    //Consulta 1
    public void consulta1(ListaArray<MovieCastMember> listaActores, MyHashTable<String, CastMember> hashCast) {
        long firstTime = System.nanoTime();
        ListaArray<CastMember> listaTop5 = new ArrayList<>(6);
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
        System.out.println( "\nTiempo de ejecución de la consulta:" + "\n" + timeTotal);
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
        lista.remove(5);// Saco el elemento minimo, lo podria descartar
        return lista.get(4).getMovieCastMemberActor().size();
    }


    // Consulta 2

    public void consulta2(ListaArray<MovieCastMember> listaProdDir, MyHashTable<String, CastMember> hashCast){

        long firstTime = System.nanoTime();
        ListaArray<CauseOfDeath> listaTop5Death = new ArrayList<>(5);
        MyHashTable<String,Integer> hashCantCausas = new MyClosedHashImpl<>(800,1f);
        MyHashTable<String,CastMember> hashProdDir = new MyClosedHashImpl<>(5000,1f);

        for(int i = 0; i < listaProdDir.size(); i++){ //  Recorro la lista de productores y directores
            CastMember temp = hashCast.get(listaProdDir.get(i).getImdbNameId());
                if (!hashProdDir.contains(temp.getImdbNameId()) && temp.getCausasDeMuerte() != null && (temp.getBirthCountry().contains("UK") || temp.getBirthCountry().contains("USA") || temp.getBirthCountry().contains("France") || temp.getBirthCountry().contains("Italy"))) {
                    hashProdDir.put(temp.getImdbNameId(), temp);
                    Integer causa = hashCantCausas.get(temp.getCausasDeMuerte().getName());
                    if (causa != null) {
                        causa++;
                        hashCantCausas.put(temp.getCausasDeMuerte().getName(), causa);
                        causa = hashCantCausas.get(temp.getCausasDeMuerte().getName());
                    } else {
                        hashCantCausas.put(temp.getCausasDeMuerte().getName(), 1);
                        causa = hashCantCausas.get(temp.getCausasDeMuerte().getName());
                    }
                    if (listaTop5Death.size() < 5) {
                        listaTop5Death.addLast(temp.getCausasDeMuerte());
                    }else {
                        int pos = listaTop5Death.find(temp.getCausasDeMuerte());
                        if (pos != -1) {
                            this.acomodarMuertes(pos, hashCantCausas, listaTop5Death);
                        } else if (causa > hashCantCausas.get(listaTop5Death.get(4).getName())) {
                            listaTop5Death.addPisando(temp.getCausasDeMuerte(), 4);
                            pos = 4;
                            this.acomodarMuertes(pos, hashCantCausas, listaTop5Death);
                        }
                    }
                }
        }
        long lastTime = System.nanoTime();
        long dif2 = lastTime - firstTime;
        double timeTotal = (double) dif2/1000000000;
        // Imprimo en pantalla lo correspondiente a la consulta 2
        for (int k = 0; k < 5; k++) {
            System.out.println("\nCausa de muerte: " + listaTop5Death.get(k).getName() + "\nCantidad de personas: " + hashCantCausas.get(listaTop5Death.get(k).getName()) + "\n");
        }
        System.out.println( "\nTiempo de ejecución de la consulta:" + "\n" + timeTotal);
    }


    // Consulta 3
    public void consulta3(ListaArray<ListaArray<Movie>> listaMoviesAños, MyHashTable<String,CastMember> hashCastMmeber) {

        long firstTime = System.nanoTime();
        ListaArray<Movie> listaPeliMasWA = new ArrayList<>(14);
        ListaArray<Float> listaPromedios = new ArrayList<>(14);
        
        ListaArray<Movie> temp = listaMoviesAños.get(4);
        MyHeap<Float, Movie> heap = heapSort(temp);
        while (listaPeliMasWA.size() < 14) {
            Movie peliculaAgregar = heap.delete();
            if (peliculaAgregar.getYear() <= 1960) {
                listaPeliMasWA.addLast(peliculaAgregar);
            }
        }
        // En este punto ya tengo mi lista de lista de 14 peliculas
        for (int i = 0; i < 14; i++) {
            Movie temp3 = listaPeliMasWA.get(i);
            float promedio = 0;
            int divisor = 0;
            for (int j = 0; j < temp3.getMovieCastMemberActores().size(); j++) {
                MovieCastMember mcm = temp3.getMovieCastMemberActores().get(j);
                int temp4 = hashCastMmeber.get(mcm.getImdbNameId()).getHeight();
                if (temp4 != 0) {
                    divisor++;
                    promedio = promedio + temp4;
                }
            }
            listaPromedios.addLast(promedio / divisor);
        }
        long lastTime = System.nanoTime();
        long dif2 = lastTime - firstTime;
        double timeTotal = (double) dif2/1000000000;
        for (int i = 0; i < 14; i++) {
            if (!listaPromedios.get(i).isNaN()) {
                System.out.println("\nId película: " + listaPeliMasWA.get(i).getImdbTitleld() + "\nNombre: " + listaPeliMasWA.get(i).getTitle() + "\nAltura promedio de actores: " + listaPromedios.get(i));
            }
        }
        System.out.println( "\nTiempo de ejecución de la consulta:" + "\n" + timeTotal);
    }

    // Consulta 4
    public void consulta4(MyHashTable<String,CastMember> hashCastMmeber, ListaArray<MovieCastMember> listaActores) {
        long firstTime = System.nanoTime();
        MyHashTable<Integer, Integer> yearActores = new MyClosedHashImpl<>(500, 1F);
        MyHashTable<Integer, Integer> yearActrices = new MyClosedHashImpl<>(500, 1F);
        MyHashTable<String, CastMember> hashTotal = new MyClosedHashImpl<>(100000, 1F);
        int cant=0;
        for (int i = 0; i < listaActores.size(); i++) { //  Recorro la lista de actores y actrices
            CastMember temp = hashCastMmeber.get(listaActores.get(i).getImdbNameId());
            if (temp.getBirthDate() != null && !hashTotal.contains(temp.getImdbNameId())) {
                hashTotal.put(temp.getImdbNameId(), temp);
                String genre = listaActores.get(i).getCategory();
                if (genre.toLowerCase().equals("actor")) {
                    if(temp.getBirthDate()==1970){
                        cant++;
                    }
                    Integer dateMen = yearActores.get(temp.getBirthDate());
                    if (dateMen == null) {
                        yearActores.put(temp.getBirthDate(), 1);
                    } else {
                        dateMen++;
                        yearActores.put(temp.getBirthDate(), dateMen);
                    }
                } if (genre.toLowerCase().equals("actress")) {
                    Integer dateFemale = yearActrices.get(temp.getBirthDate());
                    if (dateFemale == null) {
                        yearActrices.put(temp.getBirthDate(), 1);
                    } else {
                        dateFemale++;
                        yearActrices.put(temp.getBirthDate(), dateFemale);
                    }
                }
            }
        }

        int yearMen = 0;
        Integer cantMen = 0;
        int yearWomen = 0;
        Integer cantWomen = 0;
        for(Integer j=1000;j<2021;j++){
            Integer man = yearActores.get(j);
            Integer women = yearActrices.get(j);
            if(man!=null && man>cantMen){
                cantMen = man;
                yearMen = j;
            }if(women!=null && women>cantWomen){
                cantWomen = women;
                yearWomen = j;
            }
        }
        long lastTime = System.nanoTime();
        long dif2 = lastTime - firstTime;
        double timeTotal = (double) dif2/1000000000;
        System.out.println("\nActores: "+"\nAño: "+yearMen+"\nCantidad: "+cantMen+"\nActrices: "+"\nAño: "+yearWomen+"\nCantidad: "+cantWomen);
        System.out.println("Tiempo de ejecucion de la consulta: "+timeTotal);
        }



    // Consulta 5
    public void consulta5(ListaArray<MovieCastMember> listaActores, MyHashTable<String,CastMember> hashCastMmeber, MyHashTable<String, Movie> hashMovies){

        long firstTime = System.nanoTime();
        MyHashTable<String, Integer> hashGenerosCant = new MyClosedHashImpl<>(50, 1F); //Hay 23 generos
        MyHashTable<String, Movie> hashTotal = new MyClosedHashImpl<>(80000, 1F);// Hay 46521
        MyHashTable<String, MovieCastMember> auxiliarActoresConDosHijos = new MyClosedHashImpl<>(15000, 1f);// Hay 8842
        ListaArray<String> listKeys = new ArrayList<>(50);

        for(int i = 0;i < listaActores.size();i++){ // Recorro la lista de actores de MCM
            Movie movie = hashMovies.get(listaActores.get(i).getImdbTitleId());
            if(!hashTotal.contains(movie.getImdbTitleld())) {
                if (auxiliarActoresConDosHijos.contains(listaActores.get(i).getImdbNameId())) {
                    ListaArray<String> listaGeneros = movie.getGenre();
                    hashTotal.put(movie.getImdbTitleld(), movie); // Agrego la movie
                    for (int j = 0; j < listaGeneros.size(); j++) {
                        String genero = listaGeneros.get(j);
                        // Agrego al hash de generos
                        Integer tipo = hashGenerosCant.get(genero);
                        if (tipo == null) {
                            hashGenerosCant.put(genero, 1);
                            listKeys.addLast(genero);
                        } else {
                            tipo++;
                            hashGenerosCant.put(genero, tipo);
                        }
                    }
                }else{
                    CastMember castMember = hashCastMmeber.get(listaActores.get(i).getImdbNameId()); //  Movie Cast Member que estoy evaluando
                    if (castMember.getChildren() >= 2) {
                        //Agrego a la lista auxiliar
                        auxiliarActoresConDosHijos.put(castMember.getImdbNameId(),listaActores.get(i));
                        hashTotal.put(movie.getImdbTitleld(),movie);
                        ListaArray<String> listaGeneros = movie.getGenre();
                        for(int k = 0; k < listaGeneros.size(); k++){
                            String genero = listaGeneros.get(k);
                            // Agrego al hash de generos
                            Integer tipo = hashGenerosCant.get(genero);
                            if (tipo == null) {
                                hashGenerosCant.put(genero, 1);
                                listKeys.addLast(genero);
                            } else {
                                tipo++;
                                hashGenerosCant.put(genero, tipo);
                            }
                        }
                    }
                }
            }
        }
        // A este punto ya tengo el hash de generos con apariciones
        boolean estaOrdenado = false;
        MyHeap<Integer, String> heap = new MyHeapImpl<>(50, true); // Ingreso en un heap maximo
        int posicion = -1;
        while (++posicion < listKeys.size()){
            heap.insert(hashGenerosCant.get(listKeys.get(posicion)),listKeys.get(posicion));
        }
        for(int i = 0; i < 10; i++){
            String gen = heap.delete(); // Me va a dar el nombre del genero
            int cant =hashGenerosCant.get(gen); // Me va a dar la cantidad
            System.out.println("\nGenero pelicula: " + gen + "\nCantidad: " + cant);
        }

        long lastTime = System.nanoTime();
        long dif2 = lastTime - firstTime;
        double timeTotal = (double) dif2/1000000000;
        System.out.println("Tiempo de ejecucion de la consulta: "+timeTotal);
    }





    public static MyHeap<Float, Movie> heapSort(ListaArray<Movie> lista) {

        MyHeap<Float, Movie> heap = new MyHeapImpl<>(lista.size(), true); // Ingreso en un heap maximo
        for(int i = 0; i < lista.size(); i++){
            heap.insert(lista.get(i).getMovieRating().getWeightedAverage(), lista.get(i));
        }
        return heap;
    }

    /*public static void heapSort(ListaArray<Movie> lista) {
    int heapLenght = 1;
    int posicion;
    for (int i = 0; i < lista.size() - 1; i++) {
        posicion = heapLenght;
        while (posicion != 0 && lista.get(posicion).compareTo(lista.get(obtenerPosicionPadre(posicion))) < 0) {
            Movie temp = lista.get(posicion);
            lista.addPisando(lista.get(obtenerPosicionPadre(posicion)),posicion);
            lista.addPisando(temp,obtenerPosicionPadre(posicion));
            posicion = obtenerPosicionPadre(posicion);
        }
        heapLenght++;
    }
    for (int i = 0; i < lista.size(); i++) {
        Movie agregarAlFinal = lista.get(0);
        lista.addPisando(lista.get(heapLenght-1), 0);
        boolean noEstaOrdenado = true;
        posicion = 0;
        while (noEstaOrdenado) {
            if (obtenerPosicionHijoIzquierdo(posicion) >= heapLenght && obtenerPosicionHijoDerecho(posicion) >= heapLenght) {
                noEstaOrdenado = false;
            } else if (obtenerPosicionHijoDerecho(posicion) < heapLenght && obtenerPosicionHijoIzquierdo(posicion) < heapLenght) {
                int posicionMenor;
                if (lista.get(obtenerPosicionHijoDerecho(posicion)).compareTo(lista.get(obtenerPosicionHijoIzquierdo(posicion))) < 0) {
                    posicionMenor = obtenerPosicionHijoDerecho(posicion);
                } else {
                    posicionMenor = obtenerPosicionHijoIzquierdo(posicion);
                }
                if (lista.get(posicion).compareTo(lista.get(posicionMenor)) > 0) {
                    Movie temp = lista.get(posicionMenor);
                    lista.addPisando(lista.get(posicionMenor),posicion);
                    lista.addPisando(temp,posicionMenor);
                    posicion = posicionMenor;
                } else {
                    noEstaOrdenado = false;
                }
            } else if (obtenerPosicionHijoIzquierdo(posicion) < heapLenght) {
                if (lista.get(posicion).compareTo(lista.get(obtenerPosicionHijoIzquierdo(posicion))) > 0) {
                    Movie temp = lista.get(posicion);
                    lista.addPisando(lista.get(obtenerPosicionHijoIzquierdo(posicion)),posicion);
                    lista.addPisando(temp,obtenerPosicionHijoIzquierdo(posicion));
                }
                noEstaOrdenado = false;
            } else {
                if (lista.get(posicion).compareTo(lista.get(obtenerPosicionHijoDerecho(posicion))) > 0) {
                    Movie temp = lista.get(posicion);
                    lista.addPisando(lista.get(obtenerPosicionHijoDerecho(posicion)),posicion);
                    lista.addPisando(temp,obtenerPosicionHijoDerecho(posicion));
                }
                noEstaOrdenado = false;
            }
        }
        lista.addPisando(agregarAlFinal, heapLenght-1);
        heapLenght--;
    }
}


    public static int obtenerPosicionHijoDerecho(int posicion) {
        return (2 * posicion + 2);
    }

    public static int obtenerPosicionHijoIzquierdo(int posicion) {
        return (2 * posicion + 1);
    }

    public static int obtenerPosicionPadre(int posicion) {
        return ((posicion - 1) / 2);
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










