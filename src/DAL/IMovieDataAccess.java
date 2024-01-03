package DAL;

import BE.Movie;

import java.util.List;

public interface IMovieDataAccess {
    public List<Movie> getAll() throws Exception;

    public Movie create(Movie movie) throws Exception;

    public void update(Movie movie) throws Exception;

    public void delete(Movie movie) throws Exception;

    public List<Movie> getByCatId(int catId) throws Exception;

    public Movie getById(int movieId) throws Exception;
}
