package entities;

import TADs.arraylist.ListaArray;

public class MovieCastMember {

    private String imdbTitleId;
    private String imdbNameId;
    private Integer ordering;
    private String category;
    private String job;
    private ListaArray<String> characters;

    public MovieCastMember(String imdbTitleId, String imdbNameId,Integer ordering, String category, String job, ListaArray<String> characters) {
        this.imdbNameId= imdbNameId;
        this.imdbTitleId = imdbTitleId;
        this.ordering = ordering;
        this.category = category;
        this.job = job;
        this.characters = characters;
    }

    public String getImdbTitleId() {
        return imdbTitleId;
    }

    public String getImdbNameId() {
        return imdbNameId;
    }

    public Integer getOrdering() {
        return ordering;
    }

    public String getCategory() {
        return category;
    }

    public String getJob() {
        return job;
    }

    public ListaArray<String> getCharacters() {
        return characters;
    }


    public boolean equals(Object obj){
        if (this == obj){
            return true;
        }else if(obj == null){
            return false;
        }else if (getClass() != obj.getClass()){
            return false;
        }else{
            MovieCastMember movieCastMemberAComparar = (MovieCastMember) obj;
            return (this.getImdbNameId()).equals(movieCastMemberAComparar.getImdbNameId()) && this.getImdbTitleId().equals(movieCastMemberAComparar.getImdbTitleId());
        }
    }

}


