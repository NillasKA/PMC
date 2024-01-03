package BE;

public class CatMovie {

    private int categoryId;
    private int movieId;

    public CatMovie(int categoryId, int movieId)
    {
        this.categoryId = categoryId;
        this.movieId = movieId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public int getMovieId() {
        return movieId;
    }

    @Override
    public String toString() {
        return "CatMovie{" +
                "categoryId=" + categoryId +
                ", movieId=" + movieId +
                '}';
    }
}
