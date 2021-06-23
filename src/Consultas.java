
import TADs.arraylist.ArrayList;
import TADs.arraylist.ListaArray;
import TADs.hash.MyHash.MyHashTable;
import entities.CastMember;
import entities.CauseOfDeath;
import entities.MovieCastMember;

public class Consultas {

    private static boolean seRealizoConsulta1 = false;
    private static boolean seRealizoConsulta2 = false;
    private ListaArray<CastMember> listaTop5Actores = new ArrayList<>(5);
    private ListaArray<CauseOfDeath> listaTop5Death = new ArrayList<>(5);

    // Constructor
    public Consultas() {    }

    public static boolean isSeRealizoConsulta1() {
        return seRealizoConsulta1;
    }

    public ListaArray<CastMember> getListaTop5() {
        return listaTop5Actores;
    }

    public void setListaTop5(ListaArray<CastMember> listaTop5) {
        this.listaTop5Actores = listaTop5;
    }

    //Consulta 1
    public void consulta1(ListaArray<MovieCastMember> listaActores, MyHashTable<String, CastMember> hashCast){

        // Lleno mi arraylist inicialmente con los primero cinco elementos
        for(int i = 0; i < 5; i++){ // No se controla que en los 5 primeros actores se repita alguno de ellos pues seria absurdo.
            CastMember temp = hashCast.get(listaActores.get(i).getImdbNameId());
            temp.aumentarApariciones();
            this.listaTop5Actores.addLast(temp);
        }

        for(int i = 5; i < listaActores.size(); i++){
            CastMember temp = hashCast.get(listaActores.get(i).getImdbNameId());
            temp.aumentarApariciones();
            int pos = this.listaTop5Actores.find(temp);
            if(pos != -1){
                this.acomodarActores(pos);
            }else if (temp.getApariciones() > this.listaTop5Actores.get(4).getApariciones()) {
                this.listaTop5Actores.addPisando(temp, 4);
                pos = 4;
                this.acomodarActores(pos);
            }
        }
        // Imprimo en pantalla lo correspondiente a la consulta 1

        for(int k = 0; k < 5; k++){
            System.out.println("\nNombre actor/actriz: " + this.listaTop5Actores.get(k).getName() + "\nCantidad de apariciones : " + this.listaTop5Actores.get(k).getApariciones() + "\n");
        }
        seRealizoConsulta1 = true;
    }


   // Consulta 2
    public void consulta2(ListaArray<MovieCastMember> listaProdDir, MyHashTable<String, CastMember> hashCast){

        for(int i = 0; i < listaProdDir.size(); i++){ //  Recorro la lista de productores y directores

            CastMember temp = hashCast.get(listaProdDir.get(i).getImdbNameId());

            if(temp.getCausasDeMuerte() != null && !(temp.getCausasDeMuerte().getName().toLowerCase().contains("undisclosed")) && (temp.getBirthCountry().contains("UK") || temp.getBirthCountry().contains("USA") || temp.getBirthCountry().contains("France") || temp.getBirthCountry().contains("Italy"))){
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
                }
            }
    // Imprimo en pantalla lo correspondiente a la consulta 2
        for(int k = 0; k < 5; k++){
        System.out.println("\nCausa de muerte: " + this.listaTop5Death.get(k).getName() + "\nCantidad de personas: " + this.listaTop5Death.get(k).getCantidadDeFallecidos() + "\n");
    }
        seRealizoConsulta2 = true;
    }


    // Consulta 3







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










