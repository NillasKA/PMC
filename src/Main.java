import BE.CatMovie;
import BE.Category;
import BE.Movie;
import GUI.Model.CatModel;
import GUI.Model.CatMovieModel;
import GUI.Model.MovieModel;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import utility.PMCException;

import java.io.IOException;
import java.util.List;

public class Main extends Application {

    public void start(Stage primaryStage) throws PMCException
    {
        FXMLLoader loader = new FXMLLoader();
        Parent root = null;
        try {
            root = loader.load(getClass().getResource("view/Warning.fxml"));
        } catch (IOException e) {
            throw new PMCException(e);
        }

        primaryStage.setTitle("Private Movie Collection");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    public static void main(String[] args) throws PMCException {
        launch(args);
        MovieModel movieModel = MovieModel.getInstance();
        CatModel catModel = CatModel.getInstance();
        CatMovieModel catMovieModel = CatMovieModel.getInstance();
        List<Movie> movies = movieModel.getAll();
        List<Category> cats = catModel.getAll();
        List<CatMovie> catMovie = catMovieModel.getAll();
    }
}
