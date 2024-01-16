package GUI.Model;

import BE.CatMovie;
import BE.Category;
import BE.Movie;
import BLL.CatMovieManager;
import BLL.MovieManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import utility.PMCException;

import java.util.List;

public class CatMovieModel {
    private static CatMovieModel instance;
    private CatMovieManager catMovieManager;
    private MovieModel movieModel;
    private CatModel catModel;
    private ObservableList<Movie> allCatMovies;
    private Category category;

    public CatMovieModel() throws PMCException {
        catMovieManager = new CatMovieManager();
        movieModel = MovieModel.getInstance();
        catModel = CatModel.getInstance();
        allCatMovies = FXCollections.observableArrayList();
    }

    public static CatMovieModel getInstance() throws PMCException {
        if(instance == null)
        {
            instance = new CatMovieModel();
        }
        return instance;
    }

    /*
    If this method is called in Initializable(), it fails as getCategory is null
    so the list is only instanciated when a category has been selected.
    This can be seen in MainViewController in the clickCategory() method.
     */
    public void initCurrentCat() throws PMCException {
        allCatMovies.clear();
        allCatMovies.addAll(catMovieManager.getMovieByCatId(catModel.getCategory().getId()));
    }

    public List<CatMovie> getAll() throws PMCException {
        return catMovieManager.getAll();
    }

    public ObservableList<Movie> getObservableCatMovies() throws Exception {
        return allCatMovies;
    }

    public int getMoviesCountForCategory(int categoryId) throws PMCException
    {
        return catMovieManager.getMoviesCountForCategory(categoryId);
    }

    public CatMovie create(CatMovie catMovie) throws PMCException {
        System.out.println("last CATMOVIE CREATION = " + movieModel.getAll().getLast().getName());
        allCatMovies.add(movieModel.getAll().getLast());
        return catMovieManager.create(catMovie);
    }

    public void update(CatMovie catMovie) throws PMCException {
        catMovieManager.update(catMovie);
    }

    public void delete(CatMovie catMovie) throws PMCException {
        catMovieManager.delete(catMovie);
        allCatMovies.remove(movieModel.getCurrentMovie());
    }

    //NOT IMPLEMENTED
    public CatMovie getById(int catMovieId) throws PMCException {
        return catMovieManager.getById(catMovieId);
    }
}
