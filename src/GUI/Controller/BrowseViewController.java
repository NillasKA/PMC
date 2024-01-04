package GUI.Controller;

import GUI.Model.CatModel;
import GUI.Model.CatMovieModel;
import GUI.Model.MovieModel;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class BrowseViewController implements Initializable {

    private MovieModel movieModel;

    private CatModel catModel;

    private CatMovieModel catMovieModel;

    private CategoryViewController categoryViewController;

    private MainViewController mainViewController;


    public BrowseViewController()
    {
        try{
            movieModel = MovieModel.getInstance();
            catMovieModel = CatMovieModel.getInstance();
            catModel = CatModel.getInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
