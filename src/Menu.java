
import java.io.IOException;
import java.util.Scanner;

public class Menu {

    public Menu() { } // Constructor

    public static boolean numberIsCorrect(String cadena, int numeroChico, int numeroGrande) {

        boolean resultado;

        try {
            Integer.parseInt(cadena);
            resultado = true;
        } catch (NumberFormatException excepcion) {
            resultado = false;
        }
        if(resultado){
            if(!(Integer.parseInt(cadena) >= numeroChico && Integer.parseInt(cadena) <= numeroGrande)){
                resultado = false;
            }
        }

        return resultado;
    }

    public static void CargaDeDatos() throws IOException {
        upData newUp = new upData();
        newUp.upNames();
        newUp.upMovies();
        newUp.uPTitle();
        newUp.upMRatin();

        System.out.println("Carga de datos exitosa, tiempo de ejecución de la carga:" + "PONER ACA TIEMPO DE CARGA DE DATOS");
    }


    public static String MenuInicial(){

        System.out.println("Seleccione la opción que desee:\n1.Carga de datos\n2.Ejecutar consultas\n3.Salir");
        Scanner entradaScanMenu = new Scanner(System.in);
        String entradaMenu = entradaScanMenu.nextLine();
        if(!numberIsCorrect(entradaMenu,1,3)){
            entradaMenu = "Dato mal ingresado, intente ingresarlo nuevamente";
        }
        return entradaMenu;
    }




    public static void main(String[] args) throws IOException {
        upData newUp = new upData();
        while(true){

            String NroMenu = MenuInicial();

            if(NroMenu.equals("1")){
                //CargaDeDatos();
                newUp.upNames();
                newUp.upMovies();
                newUp.uPTitle();
                newUp.upMRatin();
            }
            else if(NroMenu.equals("2")){
                Consultas nuevaConsulta = new Consultas();
                while(true){
                    System.out.println("1.Indicar el Top 5 de actores/actrices que más apariciones han tenido a lo largo de los años." + "\n2.Indicar el Top 5 de las causas de muerte más frecuentes en directores y productores nacidos en Italia, Estados Unidos, Francia y UK." +
                            "\n3.Mostrar de las 14 películas con más weightedAverage, el promedio de altura de sus actores/actrices si su valor es distinto de nulo." +
                            "\n4.Indicar el año más habitual en el que nacen los actores y las actrices." +
                            "\n5.Indicar  el  Top 10 de géneros  de películas  más  populares,  en  las cuales al menos un actor/actriz tiene 2 o más hijos." +
                            "\n6.Salir.");
                    Scanner entradaScanConsultas = new Scanner(System.in);
                    String entradaConsultas = entradaScanConsultas.nextLine();
                    if(!numberIsCorrect(entradaConsultas, 1, 6)){
                        System.out.println("Dato mal ingresado, intente ingresarlo nuevamente");
                    }else{// En este caso va a ejectuarse la consulta
                        if(entradaConsultas.equals("1")){

                            nuevaConsulta.consulta1(newUp.getListaMovieCastMmeber().get(0), newUp.getHashCastMember());

                        }else if(entradaConsultas.equals("2")){

                            nuevaConsulta.consulta2(newUp.getListaMovieCastMmeber().get(1), newUp.getHashCastMember());

                        }else if(entradaConsultas.equals("3")) {

                            // Aca se llama al metodo que ejecuta la consulta 3

                        }else if(entradaConsultas.equals("4")) {

                            // Aca se llama al metodo que ejecuta la consulta 4

                        }else if(entradaConsultas.equals("5")){

                            // Aca se llama al metodo que ejecuta la consulta 5

                        }else{ // Ya habia controlado arriba que el numero este entre 1 y 6 por lo que este es el caso de que digite 6.
                            // Vuelve al menu principal
                            break;
                        }
                    }
                }
            }
            else{ // (NroMenu.equals("3")) Ya habia controlado arriba que el numero este entre 1 y 3 por lo que este es el caso de que digite 3.
                System.out.println("Se terminó el programa");
                break;
            }
        }
    }
}