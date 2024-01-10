package BLL;

import BE.Movie;
import BLL.util.MovieSearcher;
import DAL.Dao.MovieDAO;
import DAL.IMovieDataAccess;
import utility.PMCException;

import java.util.List;

public class MovieManager {
    private MovieSearcher movieSearcher = new MovieSearcher();
    private IMovieDataAccess movieDAO;
    public MovieManager() throws PMCException {
        movieDAO = new MovieDAO();
    }

    public List<Movie> getAll() throws PMCException {
        return movieDAO.getAll();
    }

    public Movie create(Movie movie) throws PMCException {
        return movieDAO.create(movie);
    }

    public void update(Movie movie) throws PMCException {
        movieDAO.update(movie);
    }

    public void delete(Movie movie) throws PMCException {
        movieDAO.delete(movie);
    }

    public List<Movie> searchMovie(String query) throws PMCException
    {
        List<Movie> allMovies = getAll();
        List<Movie> searchResult = movieSearcher.search(allMovies, query);
        return searchResult;
    }

    //TODO FIRST IMPLEMENT IN MOVIEDAO THEN HERE
    public List<Movie> getByCatId(int catId) throws PMCException {
        return movieDAO.getByCatId(catId);
    }

    public Movie getById(int movieId) throws PMCException {
        return movieDAO.getById(movieId);
    }
}
