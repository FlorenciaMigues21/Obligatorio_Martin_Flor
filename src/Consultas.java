
import TADs.arraylist.ArrayList;
import TADs.arraylist.ListaArray;
import TADs.hash.MyHash.MyHashTable;
import TADs.heap.MyHeap;
import TADs.heap.MyHeapImpl;
import entities.CastMember;
import entities.CauseOfDeath;
import entities.Movie;
import entities.MovieCastMember;

public class Consultas {

    private ListaArray<CastMember> listaTop5Actores = new ArrayList<>(5);
    private ListaArray<CauseOfDeath> listaTop5Death = new ArrayList<>(5);
    private ListaArray<Float> listaPromedios = new ArrayList<>(14);
    private ListaArray<Movie> listaPeliMasWA = new ArrayList<>(14);

    // Constructor
    public Consultas() {    }

    public ListaArray<CastMember> getListaTop5() {
        return listaTop5Actores;
    }

    public void setListaTop5(ListaArray<CastMember> listaTop5) {
        this.listaTop5Actores = listaTop5;
    }

    //Consulta 1
    public void consulta1(ListaArray<MovieCastMember> listaActores, MyHashTable<String, CastMember> hashCast) {



        // Lleno mi arraylist inicialmente con los primero cinco elementos
        for (int i = 0; i < 5; i++) { // No se controla que en los 5 primeros actores se repita alguno de ellos pues seria absurdo.
            CastMember temp = hashCast.get(listaActores.get(i).getImdbNameId());
            temp.aumentarApariciones();
            this.listaTop5Actores.addLast(temp);
        }

        for (int i = 5; i < listaActores.size(); i++) {
            CastMember temp = hashCast.get(listaActores.get(i).getImdbNameId());
            temp.aumentarApariciones();
            int pos = this.listaTop5Actores.find(temp);
            if (pos != -1) {
                this.acomodarActores(pos);
            } else if (temp.getApariciones() > this.listaTop5Actores.get(4).getApariciones()) {
                this.listaTop5Actores.addPisando(temp, 4);
                pos = 4;
                this.acomodarActores(pos);
            }
        }

        // Imprimo en pantalla lo correspondiente a la consulta 1
        for (int k = 0; k < 5; k++) {
            System.out.println("\nNombre actor/actriz: " + this.listaTop5Actores.get(k).getName() + "\nCantidad de apariciones : " + this.listaTop5Actores.get(k).getApariciones() + "\n");
        }

    }

   // Consulta 2
    public void consulta2(ListaArray<MovieCastMember> listaProdDir, MyHashTable<String, CastMember> hashCast){

        if(!seRealizoConsulta2){
            for(int i = 0; i < listaProdDir.size(); i++){ //  Recorro la lista de productores y directores

                CastMember temp = hashCast.get(listaProdDir.get(i).getImdbNameId());

                if(!temp.isLoagregue() && temp.getCausasDeMuerte() != null && (temp.getBirthCountry().contains("UK") || temp.getBirthCountry().contains("USA") || temp.getBirthCountry().contains("France") || temp.getBirthCountry().contains("Italy"))){
                    CauseOfDeath causa = temp.getCausasDeMuerte();
                    causa.agregarFallecido(); // Le agrego un muerto a la causa de muerte
                    if(this.listaTop5Death.size() < 5) {
                        listaTop5Death.addLast(causa);
                    }else{
                        int pos = this.listaTop5Death.find(causa);
                        if(pos != -1){
                            this.acomodarMuertes(pos);
                        }else if (causa.getCantidadDeFallecidos() > listaTop5Death.get(4).getCantidadDeFallecidos()){
                            this.listaTop5Death.addPisando(causa, 4);
                            pos = 4;
                            this.acomodarMuertes(pos);
                        }
                    }
                    temp.setLoagregue();
                }
            }
            seRealizoConsulta2 = true;
        }

    // Imprimo en pantalla lo correspondiente a la consulta 2
        for(int k = 0; k < 5; k++){
        System.out.println("\nCausa de muerte: " + this.listaTop5Death.get(k).getName() + "\nCantidad de personas: " + this.listaTop5Death.get(k).getCantidadDeFallecidos() + "\n");
        }
    }


    // Consulta 3
    public void consulta3(ListaArray<ListaArray<Movie>> listaMoviesAños, MyHashTable<String,CastMember> hashCastMmeber) {

        if(!seRealizoConsulta3){
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
                for (int j = 0; j < temp3.getMovieCastMember().size(); j++) {
                    MovieCastMember mcm = temp3.getMovieCastMember().get(j);
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
            seRealizoConsulta3 = true;
        }
        for(int i = 0; i < 14; i++){
            if(!this.listaPromedios.get(i).isNaN()){
                System.out.println("\nId película: " + this.listaPeliMasWA.get(i).getImdbTitleld() + "\nNombre: " + this.listaPeliMasWA.get(i).getTitle() + "\nAltura promedio de actores: " + listaPromedios.get(i));
            }
        }
    }

    // Consulta 4
    public void consulta4(){

    }


    public MyHeap<Float, Movie> heapSort(ListaArray<Movie> lista) {

        MyHeap<Float, Movie> heap = new MyHeapImpl<>(lista.size(), true); // Ingreso en un heap maximo
        for(int i = 0; i < lista.size(); i++){
            heap.insert(lista.get(i).getMovieRating().getWeightedAverage(), lista.get(i));
        }
        return heap;
    }

    public void acomodarActores(int pos){
        while (pos > 0) {
            if (this.listaTop5Actores.get(pos).getApariciones() > this.listaTop5Actores.get(pos - 1).getApariciones()) {
                // Hago el cambio
                this.listaTop5Actores.intercambiarMedio(pos);
                pos--;
            }else{
                break;
            }
        }
    }
    public void acomodarMuertes(int pos){
        while (pos > 0) {
            if (this.listaTop5Death.get(pos).getCantidadDeFallecidos() > this.listaTop5Death.get(pos - 1).getCantidadDeFallecidos()) {
                // Hago el cambio
                this.listaTop5Death.intercambiarMedio(pos);
                pos--;
            }else{
                break;
            }
        }
    }
}










