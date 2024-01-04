package GUI.Model;

import BE.Movie;
import BLL.MovieManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;

public class MovieModel {
    private static MovieModel instance;
    private MovieManager movieManager;

    private ObservableList<Movie> allMovies;

    public MovieModel() throws Exception {
        movieManager = new MovieManager();
        allMovies = FXCollections.observableArrayList();
        allMovies.addAll(movieManager.getAll());
    }

    public static MovieModel getInstance() throws Exception {
        if(instance == null)
        {
            instance = new MovieModel();
        }
        return instance;
    }

    public ObservableList<Movie> getObservableMovies()
    {
        return allMovies;
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

    public void searchMovie(String query) throws Exception
    {
        List<Movie> searchResults = movieManager.searchMovie(query);
        allMovies.clear();
        allMovies.addAll(searchResults);
    }

    //TODO FIRST IMPLEMENT IN MOVIEMANAGER THEN HERE
    public List<Movie> getByCatId(int catId) {
        return null;
    }

    public Movie getById(int movieId) throws Exception {
        return movieManager.getById(movieId);
    }
}
