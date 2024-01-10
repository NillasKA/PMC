package DAL;

import BE.Movie;
import utility.PMCException;

import java.util.List;

public interface IMovieDataAccess {
    public List<Movie> getAll() throws PMCException;

    public Movie create(Movie movie) throws PMCException;

    public void update(Movie movie) throws PMCException;

    public void delete(Movie movie) throws PMCException;

    public List<Movie> getByCatId(int catId) throws PMCException;

    public Movie getById(int movieId) throws PMCException;
}
