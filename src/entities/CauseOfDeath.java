package entities;

public class CauseOfDeath {

    private String name;

    public CauseOfDeath(String name) {
        this.name = name;
    }


    public String getName() {
        return name;
    }



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
