package entities;

import TADs.arraylist.ArrayList;
import TADs.arraylist.ListaArray;

public class CastMember {
    private String imdbNameId;
    private String name;
    private String birthName;
    private Integer height;
    private String bio;
    private Integer birthDate;
    private String birthState;
    private String birthCountry;
    private String birthCity;
    private Integer deathDate;
    private String deathState;
    private String deathCountry;
    private String deathCity;
    private String spousesStirng;
    private Integer spouses;
    private Integer divorces;
    private Integer spousesWithChilden;
    private Integer children;
    private CauseOfDeath causasDeMuerte;

    // MovieCastMemberActor
    private ListaArray<MovieCastMember> movieCastMemberActor;

    // MovieCastMemberProductorDirector
    private  ListaArray<MovieCastMember> movieCastMemberDirProd;

    // MovieCastMemberOtros
    private ListaArray<MovieCastMember> moviCastMemberOtros;

    public CastMember(String imdbNameId, String name, String birthName, Integer height, String bio, Integer birthDate, String birthState, String birthCountry, String birthCity, Integer deathDate, String deathState, String deathCountry, String deathCity, String spousesStirng, Integer spouses, Integer divorces, Integer spousesWithChilden, Integer children, CauseOfDeath causaDeMuerte) {
        this.imdbNameId = imdbNameId;
        this.name = name;
        this.birthName = birthName;
        this.height = height;
        this.bio = bio;
        this.birthDate = birthDate;
        this.birthState = birthState;
        this.birthCountry = birthCountry;
        this.birthCity = birthCity;
        this.deathDate = deathDate;
        this.deathState = deathState;
        this.deathCountry = deathCountry;
        this.deathCity = deathCity;
        this.spousesStirng = spousesStirng;
        this.spouses = spouses;
        this.divorces = divorces;
        this.spousesWithChilden = spousesWithChilden;
        this.children = children;
        this.causasDeMuerte = causaDeMuerte;
        this.movieCastMemberActor = new ArrayList<>(200);
        this.movieCastMemberDirProd = new ArrayList<>(200);
        this.moviCastMemberOtros = new ArrayList<>(200);
    }



    public String getImdbNameId() {
        return imdbNameId;
    }

    public String getName() {
        return name;
    }

    public String getBirthName() {
        return birthName;
    }

    public Integer getHeight() {
        return height;
    }

    public String getBio() {
        return bio;
    }

    public Integer getBirthDate() {
        return birthDate;
    }

    public String getBirthState() {
        return birthState;
    }

    public String getBirthCountry() {
        return birthCountry;
    }

    public String getBirthCity() {
        return birthCity;
    }

    public Integer getDeathDate() {
        return deathDate;
    }

    public String getDeathState() {
        return deathState;
    }

    public String getDeathCountry() {
        return deathCountry;
    }

    public String getDeathCity() {
        return deathCity;
    }

    public String getSpousesStirng() {
        return spousesStirng;
    }

    public Integer getSpouses() {
        return spouses;
    }

    public Integer getDivorces() {
        return divorces;
    }

    public Integer getSpousesWithChilden() {
        return spousesWithChilden;
    }

    public Integer getChildren() {
        return children;
    }

    public CauseOfDeath getCausasDeMuerte() {
        return causasDeMuerte;
    }

    public ListaArray<MovieCastMember> getMovieCastMemberActor() {
        return movieCastMemberActor;
    }

    public ListaArray<MovieCastMember> getMovieCastMemberDirProd() {
        return movieCastMemberDirProd;
    }

    public ListaArray<MovieCastMember> getMoviCastMemberOtros() {
        return moviCastMemberOtros;
    }


    public void agregarmovieCastMemberActores(MovieCastMember mcm){
        this.movieCastMemberActor.addLast(mcm);
    }
    public void agregarmovieCastMemberProdDire(MovieCastMember mcm){
        this.movieCastMemberDirProd.addLast(mcm);
    }
    public void agregarmovieCastMemberOtros(MovieCastMember mcm){
        this.moviCastMemberOtros.addLast(mcm);
    }

    public boolean equals(Object obj){
        if (this == obj){
            return true;
        }else if(obj == null){
            return false;
        }else if (getClass() != obj.getClass()){
            return false;
        }else{
            CastMember castAComparar = (CastMember) obj;
            return (this.getImdbNameId().equals(castAComparar.getImdbNameId()));
        }
    }
}

