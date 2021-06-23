package entities;

public class Rating {

    private Float NumberVotes;
    private Float AverageRating;


    public Rating(Float averageRating, Float numberVotes) {
        NumberVotes = numberVotes;
        AverageRating = averageRating;
    }

    public Float getNumberVotes() {
        return NumberVotes;
    }

    public Float getAverageRating() {
        return AverageRating;
    }
}

