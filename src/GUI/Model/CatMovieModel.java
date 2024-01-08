package GUI.Model;

import BE.CatMovie;
import BE.Category;
import BE.Movie;
import BLL.CatMovieManager;
import BLL.MovieManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;

public class CatMovieModel {
    private static CatMovieModel instance;
    private CatMovieManager catMovieManager;
    private ObservableList<Movie> allCatMovies;
    private Category category;

    public CatMovieModel() throws Exception {
        catMovieManager = new CatMovieManager();
        allCatMovies = FXCollections.observableArrayList();
    }

    public static CatMovieModel getInstance() throws Exception {
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
    public void initCurrentCat() throws Exception {
        allCatMovies.clear();
        allCatMovies.addAll(catMovieManager.getMovieByCatId(getCategory().getId()));
    }

    public List<CatMovie> getAll() throws Exception {
        return catMovieManager.getAll();
    }

    public ObservableList<Movie> getObservableCatMovies(int catId) throws Exception {
        return allCatMovies;
    }

    public int getMoviesCountForCategory(int categoryId) throws Exception
    {
        return catMovieManager.getMoviesCountForCategory(categoryId);
    }

    public CatMovie create(CatMovie catMovie) throws Exception {
        return catMovieManager.create(catMovie);
    }

    public void update(CatMovie catMovie) throws Exception {
        catMovieManager.update(catMovie);
    }

    public void delete(CatMovie catMovie) throws Exception {
        catMovieManager.delete(catMovie);
    }

    //NOT IMPLEMENTED
    public CatMovie getById(int catMovieId) throws Exception {
        return catMovieManager.getById(catMovieId);
    }

    public void setCategory(Category category){
        this.category = category;
    }

    public Category getCategory(){
        return category;
    }
}
