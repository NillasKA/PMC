package BE;

import java.math.RoundingMode;
import java.math.BigDecimal;

public class Movie {
    private String name;
    private String filelink;
    private String lastview;
    private String year;
    private double rating;
    private int id;

    /**
     * Creates a movie based on the given parameters.
     * @param id - Set this to -1 as default. DB will figure it out
     * @param name - Name of the movie.
     * @param rating - Movies IMDB rating. Perhaps personal rating in the future
     * @param filelink - Filepath of the movie. Data/movies/xxxx
     * @param year - Last time the movie was viewed. Check comment in constructor
     */
    public Movie(int id, String name, double rating, String filelink, String year){
        this.id = id;
        setName(name);
        setRating(rating);
        setFilelink(filelink);
        /*
        An idea we can implement.
        Lastview is default to "never" - Then when user clicks "play" in our program it
        sets the lastview to the current date. Limitations are of course if the user put in the movie
        a few days after they watched it, it wouldnt be accurate.
         */
        setYear(year);
        setLastview("never");
    }

    public String getName(){
        return name;
    }

    public double getRating() {
        return rating;
    }

    public String getFilelink() {
        return filelink;
    }

    public String getLastview() {
        return lastview;
    }

    public int getId(){
        return id;
    }
    public String getYear() {
        return year;
    }

    //TODO Implement safety measure
    public void setName(String name){
        this.name = name;
    }

    public void setYear(String year){
        String actualYear = year.substring(0, 4);

        this.year = actualYear;
    }

    //TODO Implement safety measure
    private void setLastview(String lastview) {
        /* ROUGH PSUDO CODE OF WHAT I WANT
        this.lastview = "never"

        if (hasBeenPlayed) {
            this.lastview = currentDate
        }
         */
        this.lastview = lastview;
    }

    //TODO Implement safety measure
    private void setFilelink(String filelink) {
        this.filelink = filelink;
    }

    private void setRating(double rating) {
        int decimalPlaces = 1;

        BigDecimal bigDecimal = new BigDecimal(rating);
        bigDecimal = bigDecimal.setScale(decimalPlaces, RoundingMode.HALF_UP);

        this.rating = bigDecimal.doubleValue();
    }

    @Override
    public String toString() {
        return name + " (" + year + ")";
    }
}
