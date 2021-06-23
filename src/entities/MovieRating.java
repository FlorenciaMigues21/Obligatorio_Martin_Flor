package entities;

import TADs.arraylist.ListaArray;

public class MovieRating {

    private String imdbMovie;
    private Float weightedAverage;
    private Integer totalVotes;
    private Float meanVote;
    private Float medianVote;
    private ListaArray<Integer> votesRating;
    private ListaArray<Rating> listAllGenders;
    private ListaArray<Rating> listaMales;
    private ListaArray<Rating> listFemales;
    private Rating top1000;
    private Rating us;
    private Rating non_us;

    public MovieRating(String imdbMovie, Float weightedAverage, Integer totalVotes, Float meanVote, Float medianVote, ListaArray<Integer> votesRating, ListaArray<Rating> listAllGenders, ListaArray<Rating> listaMales, ListaArray<Rating> listFemales, Rating top1000, Rating us, Rating non_us) {
        this.imdbMovie = imdbMovie;
        this.weightedAverage = weightedAverage;
        this.totalVotes = totalVotes;
        this.meanVote = meanVote;
        this.medianVote = medianVote;
        this.votesRating = votesRating;
        this.listAllGenders = listAllGenders;
        this.listaMales = listaMales;
        this.listFemales = listFemales;
        this.top1000 = top1000;
        this.us = us;
        this.non_us = non_us;
    }

    public String getImdbMovie() {
        return imdbMovie;
    }

    public Float getWeightedAverage() {
        return weightedAverage;
    }

    public Integer getTotalVotes() {
        return totalVotes;
    }

    public Float getMeanVote() {
        return meanVote;
    }

    public Float getMedianVote() {
        return medianVote;
    }

    public ListaArray<Integer> getVotesRating() {
        return votesRating;
    }

    public ListaArray<Rating> getListAllGenders() {
        return listAllGenders;
    }

    public ListaArray<Rating> getListaMales() {
        return listaMales;
    }

    public ListaArray<Rating> getListFemales() {
        return listFemales;
    }

    public Rating getTop1000() {
        return top1000;
    }

    public Rating getUs() {
        return us;
    }

    public Rating getNon_us() {
        return non_us;
    }
}



