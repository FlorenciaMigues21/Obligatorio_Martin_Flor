package entities;

public class CauseOfDeath {

    private String name;
    private int cantidadDeFallecidos;

    public CauseOfDeath(String name) {
        this.name = name;
        this.cantidadDeFallecidos = 0;
    }


    public void agregarFallecido(){
        this.cantidadDeFallecidos++;
    }

    public int getCantidadDeFallecidos() {
        return cantidadDeFallecidos;
    }

    public String getName() {
        return name;
    }


    /*// La funcion de hashcode devuelve el codigo ascii de el nombre de le enfermedad
    public int hashCode(){

        Integer devolucion = 0;

        byte[] ascii = this.getName().getBytes(StandardCharsets.US_ASCII);
        for (byte j: ascii){
            devolucion = devolucion + j;
        }
        return devolucion;
    }*/

    public boolean equals(Object obj){
        if (this == obj){
            return true;
        }else if(obj == null){
            return false;
        }else if (getClass() != obj.getClass()){
            return false;
        }else{
            CauseOfDeath causa = (CauseOfDeath) obj;
            return (this.getName().equals(causa.getName()));
        }
    }

}
