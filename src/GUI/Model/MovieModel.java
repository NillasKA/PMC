package GUI.Model;

import BE.Movie;
import BLL.MovieManager;

import java.util.List;

public class MovieModel {
    private static MovieModel instance;
    private MovieManager movieManager;

    public MovieModel() throws Exception {
        movieManager = new MovieManager();
    }

    public static MovieModel getInstance() throws Exception {
        if(instance == null)
        {
            instance = new MovieModel();
        }
        return instance;
    }

    public List<Movie> getAll() throws Exception {
        return movieManager.getAll();
    }

    public Movie create(Movie movie) throws Exception {
        return movieManager.create(movie);
    }

    public void update(Movie movie) throws Exception {
        movieManager.update(movie);
    }

    public void delete(Movie movie) throws Exception {
        movieManager.delete(movie);
    }

    //TODO FIRST IMPLEMENT IN MOVIEMANAGER THEN HERE
    public List<Movie> getByCatId(int catId) {
        return null;
    }

    public Movie getById(int movieId) throws Exception {
        return movieManager.getById(movieId);
    }
}
