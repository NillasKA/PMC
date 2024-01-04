package BLL;

import BE.Movie;
import BLL.util.MovieSearcher;
import DAL.Dao.MovieDAO;
import DAL.IMovieDataAccess;

import java.util.List;

public class MovieManager {
    private MovieSearcher movieSearcher = new MovieSearcher();
    private IMovieDataAccess movieDAO;
    public MovieManager() throws Exception {
        movieDAO = new MovieDAO();
    }

    public List<Movie> getAll() throws Exception {
        return movieDAO.getAll();
    }

    public Movie create(Movie movie) throws Exception {
        return movieDAO.create(movie);
    }

    public void update(Movie movie) throws Exception {
        movieDAO.update(movie);
    }

    public void delete(Movie movie) throws Exception {
        movieDAO.delete(movie);
    }

    public List<Movie> searchMovie(String query) throws Exception
    {
        List<Movie> allMovies = getAll();
        List<Movie> searchResult = movieSearcher.search(allMovies, query);
        return searchResult;
    }

    //TODO FIRST IMPLEMENT IN MOVIEDAO THEN HERE
    public List<Movie> getByCatId(int catId){
        return null;
    }

    public Movie getById(int movieId) throws Exception {
        return movieDAO.getById(movieId);
    }
}
