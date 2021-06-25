
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

    public static void cargaDeDatos(upData newUp) throws IOException {
        long firstTime = System.nanoTime();
        //long startTime = System.nanoTime();
        newUp.upNames();
        //long stopTime = System.nanoTime();
        //long dif = stopTime - startTime;
        //double time = (double) dif/1_000_000_000;
        //System.out.println(time);
        //startTime = System.nanoTime();
        newUp.upMovies();
        //stopTime = System.nanoTime();
        //dif = stopTime - startTime;
        //time = (double) dif/1000000000;
        //System.out.println(time);
        //startTime = System.nanoTime();
        newUp.uPTitle();
        //stopTime = System.nanoTime();
        //dif = stopTime - startTime;
        //time = (double) dif/1000000000;
        //System.out.println(time);
        //startTime = System.nanoTime();
        newUp.upMRatin();
        //stopTime = System.nanoTime();
        //dif = stopTime - startTime;
        //time = (double) dif/1000000000;
        long lastTime = System.nanoTime();
        long dif2 = lastTime - firstTime;
        double timeTotal = (double) dif2/1000000000;
        System.out.println("\nCarga de datos exitosa, tiempo de ejecución de la carga:" + timeTotal + "\n");
    }


    public static void menuInicial() throws IOException {
        upData newUp = new upData();
        Consultas nuevaConsulta = new Consultas();
        while (true) {
            System.out.println("\nSeleccione la opción que desee:\n1.Carga de datos\n2.Ejecutar consultas\n3.Salir");
            Scanner entradaScanMenu = new Scanner(System.in);
            String entradaMenu = entradaScanMenu.nextLine();
            if (!numberIsCorrect(entradaMenu, 1, 3)) {
                System.out.println("\nDato mal ingresado, intente ingresarlo nuevamente");
            } else if (entradaMenu.equals("1")) {
                cargaDeDatos(newUp);
            } else if (entradaMenu.equals("2")) {
                consultas(nuevaConsulta, newUp);
            } else { // (NroMenu.equals("3")) Ya habia controlado arriba que el numero este entre 1 y 3 por lo que este es el caso de que digite 3.
                System.out.println("\nSe terminó el programa");
                break;
            }

        }
    }


    public static void consultas (Consultas nuevaConsulta, upData newUp){
        while (true) {
            System.out.println("\n1.Indicar el Top 5 de actores/actrices que más apariciones han tenido a lo largo de los años." + "\n2.Indicar el Top 5 de las causas de muerte más frecuentes en directores y productores nacidos en Italia, Estados Unidos, Francia y UK." +
                    "\n3.Mostrar de las 14 películas con más weightedAverage, el promedio de altura de sus actores/actrices si su valor es distinto de nulo." +
                    "\n4.Indicar el año más habitual en el que nacen los actores y las actrices." +
                    "\n5.Indicar  el  Top 10 de géneros  de películas  más  populares,  en  las cuales al menos un actor/actriz tiene 2 o más hijos." +
                    "\n6.Salir.");
            Scanner entradaScanConsultas = new Scanner(System.in);
            String entradaConsultas = entradaScanConsultas.nextLine();
            if (!numberIsCorrect(entradaConsultas, 1, 6)) {
                System.out.println("Dato mal ingresado, intente ingresarlo nuevamente");
            } else {// En este caso va a ejectuarse la consulta
                if (entradaConsultas.equals("1")) {

                    nuevaConsulta.consulta1(newUp.getListaMovieCastMmeber().get(0), newUp.getHashCastMember());

                } else if (entradaConsultas.equals("2")) {

                    nuevaConsulta.consulta2(newUp.getListaMovieCastMmeber().get(1), newUp.getHashCastMember());

                } else if (entradaConsultas.equals("3")) {

                    //nuevaConsulta.consulta3(newUp.getListaPeliculasPorAño(), newUp.getHashCastMember());

                } else if (entradaConsultas.equals("4")) {

                    // Aca se llama al metodo que ejecuta la consulta 4

                } else if (entradaConsultas.equals("5")) {

                    // Aca se llama al metodo que ejecuta la consulta 5

                } else { // Ya habia controlado arriba que el numero este entre 1 y 6 por lo que este es el caso de que digite 6.
                    // Vuelve al menu principal
                    break;
                }
            }
        }
    }




    public static void main(String[] args) throws IOException {

        menuInicial();

    }
}