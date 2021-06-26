

import TADs.arraylist.ArrayList;
import TADs.arraylist.ListaArray;

import entities.Rating;

public class Util {

    public Util() {
    }




    public String[] stringPlace(String places) {
        String[] separacion;
        if (places.contains("[")) {
            separacion = places.split("\\[");
            separacion = separacion[1].split(",");
        } else {
            separacion = places.split(",");
        }
        String[] returnString = new String[3];
        if (separacion.length > 2) {
            returnString[2] = separacion[separacion.length - 3];//CIUDAD
            returnString[1] = separacion[separacion.length - 2];//STATE
            returnString[0] = separacion[separacion.length - 1];//PAIS
        }
        if (separacion.length == 2) {
            returnString[0] = separacion[1];//PAIS
            returnString[1] = separacion[0];//ESTADO
            returnString[2] = null;
        }
        if (separacion.length == 1) {
            returnString[0] = separacion[0];
            returnString[1] = null;
            returnString[2] = null;
        }
        return returnString;
    }


    public ListaArray<String> listStringsComa(String initialString) {
        String[] stringPartido = initialString.split(",");
        ListaArray<String> returnList = new ArrayList<>(stringPartido.length);
        for (String s : stringPartido) {
            returnList.addLast(s);
        }
        return returnList;
    }


    public ListaArray<String> listStringsComaSinComi(String initialString) {
        String[] stringPartido = initialString.split(",");
        ListaArray<String> returnList = new ArrayList<>(stringPartido.length);
        if(stringPartido.length==3) {
            if (stringPartido[0].charAt(0) == '\"') {
                stringPartido[0] = stringPartido[0].substring(1);
            }
            if (stringPartido[1].charAt(0) == ' ') {
                stringPartido[1] = stringPartido[1].substring(1);
            }
            if (stringPartido[2].charAt(stringPartido[2].length() - 1) == '\"' && stringPartido[2].charAt(0) == ' ') {
                stringPartido[2] = stringPartido[2].substring(1, stringPartido[2].length() - 1);
            }
        }else if(stringPartido.length==2){
            if (stringPartido[0].charAt(0) == '\"') {
                stringPartido[0] = stringPartido[0].substring(1);
            }
            if (stringPartido[1].charAt(stringPartido[1].length() - 1) == '\"' && stringPartido[1].charAt(0) == ' ') {
                stringPartido[1] = stringPartido[1].substring(1, stringPartido[1].length() - 1);
            }
        }
        for (String s : stringPartido) {
            returnList.addLast(s);
        }
        return returnList;
    }



    public ListaArray<Rating> listRatings(String[] fraccion) {
        ListaArray<Rating> returnList = new ArrayList<>((fraccion.length) / 2);
        for (int i = 0; i < fraccion.length-1; i += 2) {
            if (!fraccion[i].isEmpty() && !fraccion[i + 1].isEmpty()) {
                Rating newRating = new Rating(Float.parseFloat(fraccion[i]), Float.parseFloat(fraccion[i + 1]));
                returnList.addLast(newRating);
            } else if (!fraccion[i].isEmpty() && fraccion[i + 1].isEmpty()) {
                Rating newRating = new Rating(Float.parseFloat(fraccion[i]), null);
                returnList.addLast(newRating);
            } else if (fraccion[i].isEmpty() && !fraccion[i + 1].isEmpty()) {
                Rating newRating = new Rating(null, Float.parseFloat(fraccion[i]));
                returnList.addLast(newRating);
            }
        }
        return returnList;
    }

    public Rating newRatings(String[] fraccion) {
        Rating newRating = null;
        if (!fraccion[0].isEmpty() && !fraccion[1].isEmpty()) {
            newRating = new Rating(Float.parseFloat(fraccion[0]), Float.parseFloat(fraccion[1]));
        } else if (!fraccion[0].isEmpty() && fraccion[1].isEmpty()) {
            newRating = new Rating(Float.parseFloat(fraccion[0]), null);
        } else if (fraccion[0].isEmpty() && !fraccion[1].isEmpty()) {
            newRating = new Rating(null, Float.parseFloat(fraccion[1]));
        }

        return newRating;
    }


}








