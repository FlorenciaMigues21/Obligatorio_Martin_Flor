package entities;

import TADs.arraylist.ListaArray;

import java.nio.charset.StandardCharsets;

public class Movie {

    private String imdbTitleld;
    private String title;
    private String orginalTitle;
    private Integer year;
    private Integer datePublished;
    private ListaArray<String> genre;
    private Integer  duration;
    private ListaArray<String> country;
    private String language;
    private ListaArray<String> director;
    private ListaArray<String> writer;
    private String productionCompany;
    private ListaArray<String> actors;
    private String description;
    private Float avgVote;
    private Integer votes;
    private String budget;
    private String usaGrossIncome;
    private String worldwideGrossIncome;
    private Float metaScore;
    private Float reviewsFromUsers;
    private Float reviewsFromCritics;
    private MovieRating movieRating;


    public Movie(String imdbTitleld, String title, String orginalTitle, Integer year, Integer datePublished, ListaArray<String> genre, Integer duration, ListaArray<String> country, String language, ListaArray<String> director, ListaArray<String> writer, String productionCompany, ListaArray<String> actors, String description, Float avgVote, Integer votes, String budget, String usaGrossIncome, String worldwideGrossIncome, Float metaScore, Float reviewsFromUsers, Float reviewsFromCritics) {
        this.imdbTitleld = imdbTitleld;
        this.title = title;
        this.orginalTitle = orginalTitle;
        this.year = year;
        this.datePublished = datePublished;
        this.genre = genre;
        this.duration = duration;
        this.country = country;
        this.language = language;
        this.director = director;
        this.writer = writer;
        this.productionCompany = productionCompany;
        this.actors = actors;
        this.description = description;
        this.avgVote = avgVote;
        this.votes = votes;
        this.budget = budget;
        this.usaGrossIncome = usaGrossIncome;
        this.worldwideGrossIncome = worldwideGrossIncome;
        this.metaScore = metaScore;
        this.reviewsFromUsers = reviewsFromUsers;
        this.reviewsFromCritics = reviewsFromCritics;
        this.movieRating = null;
    }

    public String getImdbTitleld() {
        return imdbTitleld;
    }

    public String getTitle() {
        return title;
    }

    public String getOrginalTitle() {
        return orginalTitle;
    }

    public Integer getYear() {
        return year;
    }

    public Integer getDatePublished() {
        return datePublished;
    }

    public ListaArray<String> getGenre() {
        return genre;
    }

    public Integer getDuration() {
        return duration;
    }

    public ListaArray<String> getCountry() {
        return country;
    }

    public String getLanguage() {
        return language;
    }

    public ListaArray<String> getDirector() {
        return director;
    }

    public ListaArray<String> getWriter() {
        return writer;
    }

    public String getProductionCompany() {
        return productionCompany;
    }

    public ListaArray<String> getActors() {
        return actors;
    }

    public String getDescription() {
        return description;
    }

    public Float getAvgVote() {
        return avgVote;
    }

    public Integer getVotes() {
        return votes;
    }

    public String getBudget() {
        return budget;
    }

    public String getUsaGrossIncome() {
        return usaGrossIncome;
    }

    public String getWorldwideGrossIncome() {
        return worldwideGrossIncome;
    }

    public Float getMetaScore() {
        return metaScore;
    }

    public Float getReviewsFromUsers() {
        return reviewsFromUsers;
    }

    public Float getReviewsFromCritics() {
        return reviewsFromCritics;
    }

    public void setMovieRating(MovieRating movieRating){
        this.movieRating = movieRating;
    }

    /*// La funcion de hashcode devuelve el codigo ascii de el imdbTitleld
    public int hashCode(){

        int devolucion = 0;

        byte[] ascii = this.getImdbTitleld().getBytes(StandardCharsets.US_ASCII);
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
            Movie movieAComparar = (Movie) obj;
            return (this.getImdbTitleld().equals(movieAComparar.getImdbTitleld()));
        }
    }

}
