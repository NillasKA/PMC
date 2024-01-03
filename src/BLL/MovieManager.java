package BLL;

import BE.Movie;
import DAL.Dao.MovieDAO;
import DAL.IMovieDataAccess;

import java.util.List;

public class MovieManager {
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

    //TODO FIRST IMPLEMENT IN MOVIEDAO THEN HERE
    public List<Movie> getByCatId(int catId){
        return null;
    }

    public Movie getById(int movieId) throws Exception {
        return movieDAO.getById(movieId);
    }
}
