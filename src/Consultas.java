
import TADs.arraylist.ArrayList;
import TADs.arraylist.ListaArray;
import TADs.hash.MyHash.MyHashTable;
import entities.CastMember;
import entities.MovieCastMember;

import javax.xml.catalog.Catalog;

public class Consultas {

    private static boolean seRealizoConsulta1 = false;
    private ListaArray<CastMember> listaTop5 = new ArrayList<>(5);

    // Constructor
    public Consultas() {    }

    public static boolean isSeRealizoConsulta1() {
        return seRealizoConsulta1;
    }

    public ListaArray<CastMember> getListaTop5() {
        return listaTop5;
    }

    public void setListaTop5(ListaArray<CastMember> listaTop5) {
        this.listaTop5 = listaTop5;
    }

    //Consulta 1
    public void consulta1(ListaArray<MovieCastMember> listaActores, MyHashTable<String, CastMember> hashCast){

        // Lleno mi arraylist inicialmente con los primero cinco elementos
        for(int i = 0; i < 5; i++){ // No se controla que en los 5 primeros actores se repita alguno de ellos pues seria absurdo.
            CastMember temp = hashCast.get(listaActores.get(i).getImdbNameId());
            temp.aumentarApariciones();
            this.listaTop5.addLast(temp);
        }

        for(int i = 5; i < listaActores.size(); i++){
            CastMember temp = hashCast.get(listaActores.get(i).getImdbNameId());
            temp.aumentarApariciones();
            int pos;
            if(listaTop5.find(temp) != -1){
                pos = listaTop5.find(temp);
                while (pos > 0) {
                    if (this.listaTop5.get(pos).getApariciones() > this.listaTop5.get(pos - 1).getApariciones()) {
                        // Hago el cambio
                        CastMember temp2 = this.listaTop5.get(pos);
                        this.listaTop5.addPisando(this.listaTop5.get(pos - 1), pos);
                        this.listaTop5.addPisando(temp2, pos - 1);
                        pos--;
                    } else {
                        break;
                    }
                }
            }else if (temp.getApariciones() > this.listaTop5.get(4).getApariciones()) {
                this.listaTop5.addPisando(temp, 4);
                pos = 4;
                while (pos > 0) {
                    if (this.listaTop5.get(pos).getApariciones() > this.listaTop5.get(pos - 1).getApariciones()) {
                        // Hago el cambio
                        CastMember temp2 = this.listaTop5.get(pos);
                        this.listaTop5.addPisando(this.listaTop5.get(pos - 1), pos);
                        this.listaTop5.addPisando(temp2, pos - 1);
                        pos--;
                    }else{
                        break;
                    }
                }
            }
        }
        // Imprimo en pantalla lo correspondiente a la consulta 1

        for(int k = 4; k >= 0; k--){
            System.out.println("\nNombre actor/actriz: " + this.listaTop5.get(k).getName() + "\nCantidad de apariciones : " + this.listaTop5.get(k).getApariciones() + "\n");
        }
        seRealizoConsulta1 = true;
    }

}
