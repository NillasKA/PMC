package GUI.Model;

import BE.Movie;
import BLL.MovieManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import utility.PMCException;

import java.time.LocalDate;
import java.util.List;

public class MovieModel {
    private Movie currentMovie;
    private static MovieModel instance;
    private MovieManager movieManager;
    private ObservableList<Movie> allMovies;
    private LocalDate currentDate = LocalDate.now();
    private int currentYear;

    public MovieModel() throws PMCException {
        currentYear = currentDate.getYear();
        movieManager = new MovieManager();
        allMovies = FXCollections.observableArrayList();
        allMovies.addAll(movieManager.getAll());
    }

    public static MovieModel getInstance() throws PMCException {
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

    public List<Movie> getAll() throws PMCException {
        return movieManager.getAll();
    }

    public Movie create(Movie movie) throws PMCException {
        allMovies.add(movie);
        return movieManager.create(movie);
    }

    public void update(Movie movie) throws PMCException {
        movieManager.update(movie);
    }

    public void delete(Movie movie) throws PMCException {
        movieManager.delete(movie);
        allMovies.remove(movie);
    }

    public void searchMovie(String query) throws PMCException
    {
        List<Movie> searchResults = movieManager.searchMovie(query);
        allMovies.clear();
        allMovies.addAll(searchResults);
    }

    public List<Movie> getByCatId(int catId) throws PMCException {
        return movieManager.getByCatId(catId);
    }

    public Movie getById(int movieId) throws PMCException {
        return movieManager.getById(movieId);
    }

    public void setMovie(Movie movie){
        this.currentMovie = movie;
    }
    public Movie getCurrentMovie(){
        return currentMovie;
    }
}
