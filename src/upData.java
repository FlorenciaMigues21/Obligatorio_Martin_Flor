
import TADs.arraylist.ArrayList;
import TADs.arraylist.ListaArray;
import TADs.hash.MyHash.MyClosedHashImpl;
import TADs.hash.MyHash.MyHashTable;
import entities.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Date;
import java.util.Locale;

public class upData {

    public upData(){ }

    /* PRUEBAS Y TESTEO DE CANTIDADES
    private ListaArray<CastMember> listCast = new ArrayList<>(297706);
    private ListaArray<Movie> listMovie = new ArrayList(85856);
    private ListaArray<MovieCastMember> listMovieCast = new ArrayList<>(835494);
    private ListaArray<MovieRating> listMovieRaiting = new ArrayList<>(85856);*/

    // Inicializacion de las estructuras donde guardaremos los datos
    private MyHashTable<String, CastMember> hashCastMember = new MyClosedHashImpl(297706,1f);
    private MyHashTable<String,Movie> hashPeliculas = new MyClosedHashImpl<>(85856, 1f);
    private ListaArray<ListaArray<MovieCastMember>> listaMovieCastMmeber = new ArrayList<>(3);

    //Getters de las estructuras
    public MyHashTable<String, CastMember> getHashCastMember() {
        return hashCastMember;
    }
    public MyHashTable<String, Movie> getHashPeliculas() {
        return hashPeliculas;
    }
    public ListaArray<ListaArray<MovieCastMember>> getListaMovieCastMmeber() {
        return listaMovieCastMmeber;
    }

    util funciones = new util();

    public void upNames() throws IOException {
        FileReader file = new FileReader("C:\\Users\\flopy\\Desktop\\dataset(1)\\IMDb names.csv");
        BufferedReader reader = new BufferedReader(file);
        MyHashTable<String, CauseOfDeath> hashCausasDeMuerte = new MyClosedHashImpl(18294,0.99999f); // El load factor no me importa pues nunca habra rehashing

        String[] miniSrtings = new String[17];
        reader.readLine(); //Para saltear la primera linea que es la que contiene info innecesaria
        int start;
        String line;
        int columna = 0;
        int current;
        boolean comillas = false;
        while ((line = reader.readLine()) != null) {
            start = 0;
            for (current = 0; current < line.length(); current++) {
                if (line.charAt(current) == '\"') {
                    comillas = !comillas;
                } else if (line.charAt(current) == ',' && !comillas) {
                    miniSrtings[columna] = line.substring(start, current);//FIXME
                    columna++;
                    start = current + 1;
                }
            }
            if (columna == 16) {
                miniSrtings[columna] = line.substring(start, current);

                Integer height = null;
                if (!miniSrtings[3].isEmpty()) {
                    height = Integer.parseInt(miniSrtings[3]);
                }
                //El ministring[5] no tiene info util
                Integer date_birth = null;
                if (!miniSrtings[6].isEmpty()) {
                    try{
                        date_birth = Integer.parseInt(miniSrtings[6].substring(0,3));
                    }catch(Exception e){
                        // NO hago nada. Solo no quiero que me salte la excepcion
                    }
                }
                String[] places_birth= new String[3];
                if (!miniSrtings[7].isEmpty()) {
                    places_birth = funciones.stringPlace(miniSrtings[7]);//RECORDAD:[0]PAIS,[1]STATE,[2]CITY
                }
                Integer date_death = null;
                //El ministring[8] no tiene info util
                if (!miniSrtings[9].isEmpty()) {
                    try{
                        date_death = Integer.parseInt(miniSrtings[9].substring(0,3));
                    }catch(Exception e){
                        // NO hago nada. Solo no quiero que me salte la excepcion
                    }

                }
                String[] places_death = new String[3];
                if (!miniSrtings[10].isEmpty()) {
                    places_death = funciones.stringPlace(miniSrtings[7]);//RECORDAD:[0]PAIS,[1]STATE,[2]CITY

                }

                ListaArray<CauseOfDeath> listaMuerte = null; // La lista que se le va a asignar a la persona
                if(!miniSrtings[11].isEmpty()){
                    String[] cause = funciones.listStringAnd(miniSrtings[11]);
                    listaMuerte = new ArrayList<>(cause.length);
                    for (String s : cause) {
                        CauseOfDeath temp2 = hashCausasDeMuerte.get(s);
                        if(temp2 == null){
                            CauseOfDeath causaAAgregar = new CauseOfDeath(s);
                            hashCausasDeMuerte.put(s,causaAAgregar);
                            listaMuerte.addLast(causaAAgregar);
                        }else{
                            listaMuerte.addLast(temp2);
                        }
                    }
                }

                Integer spouses = null;
                if (!miniSrtings[13].isEmpty()) {
                    spouses = Integer.parseInt(miniSrtings[13]);
                }
                Integer divorces = null;
                if (!miniSrtings[14].isEmpty()) {
                    divorces = (Integer.parseInt(miniSrtings[14]));
                }
                Integer spousesWithChildren = null;
                if (!miniSrtings[15].isEmpty()) {
                    spousesWithChildren = Integer.parseInt(miniSrtings[15]);
                }
                Integer children = null;
                if (!miniSrtings[15].isEmpty()) {
                    children = Integer.parseInt(miniSrtings[16]);
                }
                CastMember newMember = new CastMember(miniSrtings[0], miniSrtings[1], miniSrtings[2], height, miniSrtings[4], date_birth, places_birth[1], miniSrtings[6], places_birth[2], date_death, places_death[1], miniSrtings[10], places_death[2],
                        miniSrtings[12], spouses, divorces, spousesWithChildren, children, listaMuerte);
                // Agrego Cast Member a las estructuras

                hashCastMember.put(miniSrtings[0], newMember);

                columna = 0;
            }
        }
        System.out.println(" La cantidad de casts members que hay es : " + hashCastMember.getSize());
        System.out.println(" La cantidad de causas de muerte diferentes en el hash de causas de muerte es : " + hashCausasDeMuerte.getSize());
    }
    public void upMovies() throws IOException {
        FileReader fr = new FileReader("C:\\Users\\flopy\\Desktop\\dataset(1)\\IMDb movies.csv");
        BufferedReader reader = new BufferedReader(fr);
        String[] miniSrtings = new String[22];
        reader.readLine(); //Para saltear la primera linea que es la que contiene info innecesaria
        int start;
        String line;
        int columna = 0;
        int current;
        boolean comillas = false;
        while ((line = reader.readLine()) != null) {
            start = 0;
            for (current = 0; current < line.length(); current++) {
                if (line.charAt(current) == '\"') {
                    comillas = !comillas;
                } else if (line.charAt(current) == ',' && !comillas) {
                    miniSrtings[columna] = line.substring(start, current);
                    columna++;
                    start = current + 1;
                }
            }
            if (columna == 21) {
                miniSrtings[columna] = line.substring(start, current);

                Integer year = null;
                if (!miniSrtings[3].isEmpty()) {
                    try {
                        year = Integer.parseInt(miniSrtings[3]);
                    }catch (Exception e){
                        year = null;
                    }
                }
                Integer datePublished = null;
                if (!miniSrtings[4].isEmpty()) {
                    try{
                        datePublished = Integer.parseInt(miniSrtings[4].substring(0,3));
                    }catch(Exception e){
                        // NO hago nada. Solo no quiero que me salte la excepcion
                    }
                }
                ListaArray<String> genre = null;
                if (!miniSrtings[5].isEmpty()) {
                    genre = funciones.listStringsComa(miniSrtings[5]);
                }
                Integer duration = null;
                if(!miniSrtings[6].isEmpty()){
                    duration = Integer.parseInt(miniSrtings[6]);
                }
                ListaArray<String> country = null;
                if(!miniSrtings[7].isEmpty()){
                    country=funciones.listStringsComa(miniSrtings[7]);
                }
                ListaArray<String> director = null;
                if(!miniSrtings[9].isEmpty()){
                    director = funciones.listStringsComa(miniSrtings[9]);
                }
                ListaArray<String> writers = null;
                if(!miniSrtings[10].isEmpty()){
                    writers = funciones.listStringsComa(miniSrtings[10]);
                }
                ListaArray<String> actors = null;
                if(!miniSrtings[12].isEmpty()){
                    actors = funciones.listStringsComa(miniSrtings[12]);
                }
                Float avgVote = null;
                if(!miniSrtings[14].isEmpty()){
                    avgVote = Float.parseFloat(miniSrtings[14]);
                }
                Integer votes = null;
                if(!miniSrtings[15].isEmpty()){
                    votes = Integer.parseInt(miniSrtings[15]);
                }
                Float metaScore= null;
                if(!miniSrtings[19].isEmpty()){
                    metaScore = Float.parseFloat(miniSrtings[19]);
                }
                Float reviewsFromUsers = null;
                if(!miniSrtings[20].isEmpty()){
                    reviewsFromUsers =Float.parseFloat(miniSrtings[20]);
                }
                Float reviewsFromCritics = null;
                if(!miniSrtings[21].isEmpty()){
                    reviewsFromCritics=Float.parseFloat(miniSrtings[21]);
                }
                Movie newMovie = new Movie(miniSrtings[0],miniSrtings[1],miniSrtings[2],year,datePublished,genre,duration,country,miniSrtings[8],
                        director,writers,miniSrtings[11],actors,miniSrtings[13],avgVote,votes,miniSrtings[16],miniSrtings[17],miniSrtings[18],metaScore,reviewsFromUsers,reviewsFromCritics);
                hashPeliculas.put(miniSrtings[0], newMovie);
                columna=0;
            }
        }
        System.out.println(" La cantidad de movies que hay es : " + hashPeliculas.getSize());
    }

    public void uPTitle() throws IOException { //HAY QUE VERIFICAR QUE ESTO ESTE BIEN FIXME

        // Inicializo las tres listas de movie cast member de manera conveniente
        ListaArray<MovieCastMember> listaActores = new ArrayList<>(355742);
        ListaArray<MovieCastMember> listaProdDir = new ArrayList<>(190054);
        ListaArray<MovieCastMember> listaOtros = new ArrayList<>(289697); // Vease que la lista de otros podria, para hcer de este un mejor programa, dividirse en fotografos, camaras, etc

        listaMovieCastMmeber.add(listaActores,0);// listaMovieCastMmeber[0] = listaActores
        listaMovieCastMmeber.add(listaProdDir,1); //listaMovieCastMmeber[1] = listaProdDir
        listaMovieCastMmeber.add(listaOtros,2); // listaMovieCastMmeber[2] = listaOtros


        FileReader fr = new FileReader("C:\\Users\\flopy\\Desktop\\dataset(1)\\IMDb title_principals.csv");
        BufferedReader reader = new BufferedReader(fr);
        util funciones = new util();
        String[] miniSrtings = new String[6];
        reader.readLine(); //Para saltear la primera linea que es la que contiene info innecesaria
        int start;
        String line;
        int columna = 0;
        int current;
        boolean comillas = false;
        while ((line = reader.readLine()) != null) {
            start = 0;
            for (current = 0; current < line.length(); current++) {
                if (line.charAt(current) == '\"') {
                    comillas = !comillas;
                } else if (line.charAt(current) == ',' && !comillas) {
                    miniSrtings[columna] = line.substring(start, current);
                    columna++;
                    start = current + 1;
                }
            }
            if (columna == 5) {
                miniSrtings[columna] = line.substring(start, current);
                Integer ordering = Integer.parseInt(miniSrtings[1]);
                ListaArray<String> newLis;
                newLis = funciones.listStringsComa(miniSrtings[5]);
                MovieCastMember newMC = new MovieCastMember(miniSrtings[0],miniSrtings[2],ordering,miniSrtings[3],miniSrtings[4],newLis);

                // Agrego a las estructuras el nuevo MCM

                if (miniSrtings[3].toLowerCase().contains("actress") || miniSrtings[3].toLowerCase().contains("actor")){
                    //LO INGRESO AL ARRAY DE ACTRISES
                    listaMovieCastMmeber.get(0).addLast(newMC);
                }else if(miniSrtings[3].toLowerCase().contains("director") || miniSrtings[3].toLowerCase().contains("producer")){
                    //LO INGRESO AL ARRAY DE PRODDIR
                    listaMovieCastMmeber.get(1).addLast(newMC);
                }else{
                    //LO INGRESO A OTROS
                    listaMovieCastMmeber.get(2).addLast(newMC);
                }
                columna=0;
            }
        }
        System.out.println(" La cantidad de movies casts members ACTORES que hay es : " + listaActores.size());
        System.out.println(" La cantidad de movies casts members PRODEUCTORES que hay es : " + listaProdDir.size());
        System.out.println(" La cantidad de movies casts members OTROS que hay es : " + listaOtros.size());
    }


    public void upMRatin() throws IOException {

        FileReader file = new FileReader("C:\\Users\\flopy\\Desktop\\dataset(1)\\IMDb ratings.csv");
        BufferedReader reader = new BufferedReader(file);
        String[] miniSrtings = new String[49];
        reader.readLine(); //Para saltear la primera linea que es la que contiene info innecesaria
        int start;
        String line;
        int columna = 0;
        int current;
        boolean comillas = false;
        while ((line = reader.readLine()) != null) {
            start = 0;
            for (current = 0; current < line.length(); current++) {
                if (line.charAt(current) == '\"') {
                    comillas = !comillas;
                } else if (line.charAt(current) == ',' && !comillas) {
                    miniSrtings[columna] = line.substring(start, current);
                    columna++;
                    start = current + 1;
                }
            }
            if (columna == 48) {
                miniSrtings[columna] = line.substring(start, current);
                ListaArray<Integer> votesRating = new ArrayList<>(10);
                for(int i=5;i<15;i++){
                    votesRating.addLast(Integer.parseInt(miniSrtings[i]));
                }
                ListaArray<Rating> allGender = null;
                allGender = funciones.listRatings(Arrays.copyOfRange(miniSrtings,15,23));
                ListaArray<Rating> allMales = null;
                allMales = funciones.listRatings(Arrays.copyOfRange(miniSrtings,23,33));//PREGUNTAR SI SE PUEDE USAR
                ListaArray<Rating> allFemales = null;
                allFemales = funciones.listRatings(Arrays.copyOfRange(miniSrtings,33,43));
                Rating top1000 = funciones.newRatings(Arrays.copyOfRange(miniSrtings,43,45));
                Rating us_voters = funciones.newRatings(Arrays.copyOfRange(miniSrtings,45,47));
                Rating non_us_voters= funciones.newRatings(Arrays.copyOfRange(miniSrtings,47,49));
                MovieRating newRating = new MovieRating(miniSrtings[0],Float.parseFloat(miniSrtings[1]),Integer.parseInt(miniSrtings[2]),Float.parseFloat(miniSrtings[3]),Float.parseFloat(miniSrtings[4]),
                        votesRating,allGender,allMales,allFemales,top1000,us_voters,non_us_voters);

                // Agrego movie rating a las estructuras. Busco la pelicula y le agrego el movie rating
                Movie temp = hashPeliculas.get(miniSrtings[0]);
                temp.setMovieRating(newRating);
                columna=0;
            }
        }
    }



}
