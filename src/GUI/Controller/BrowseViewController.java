package GUI.Controller;

import BE.CatMovie;
import BE.Movie;
import GUI.Model.CatModel;
import GUI.Model.CatMovieModel;
import GUI.Model.MovieModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import utility.PMCException;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class BrowseViewController implements Initializable {
    private MovieModel movieModel;
    private CatModel catModel;
    private CatMovieModel catMovieModel;
    private CategoryViewController categoryViewController;
    private CreateMovieController createMovieController;
    private MainViewController mainViewController;

    @FXML
    private TableView<Movie> tblMovies;

    @FXML
    private TextField txtMovieSearch;

    @FXML
    private TableColumn<Movie, String> colRating = new TableColumn<>();

    @FXML
    private TableColumn<Movie, String> colName = new TableColumn<>();

    @FXML
    private TableColumn<Movie, String> colLastViewed = new TableColumn<>();

    @FXML
    private TableColumn<Movie, String> colFileLink = new TableColumn<>();

    private int calculateLevenshteinDistance(String s1, String s2) {
        int[][] distance = new int[s1.length() + 1][s2.length() + 1];

        for (int i = 0; i <= s1.length(); i++) {
            distance[i][0] = i;
        }
        for (int j = 0; j <= s2.length(); j++) {
            distance[0][j] = j;
        }

        for (int i = 1; i <= s1.length(); i++) {
            for (int j = 1; j <= s2.length(); j++) {
                int cost = (s1.charAt(i - 1) == s2.charAt(j - 1)) ? 0 : 1;
                distance[i][j] = Math.min(
                        Math.min(distance[i - 1][j] + 1, distance[i][j - 1] + 1),
                        distance[i - 1][j - 1] + cost
                );
            }
        }

        return distance[s1.length()][s2.length()];
    }

    public BrowseViewController() throws PMCException {
        try {
            movieModel = MovieModel.getInstance();
            catMovieModel = CatMovieModel.getInstance();
            catModel = CatModel.getInstance();
        } catch (Exception e) {
            throw new PMCException("could not get Model instances", e);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tblMovies.setItems(movieModel.getObservableMovies());
        colRating.setCellValueFactory(new PropertyValueFactory<>("rating"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colLastViewed.setCellValueFactory(new PropertyValueFactory<>("lastview"));
        colFileLink.setCellValueFactory(new PropertyValueFactory<>("filelink"));
        tblMovies.setEditable(true);

        try {
            System.out.println(movieModel.getAll().toString());
        } catch (PMCException e) {
            throw new RuntimeException(e);
        }

        search();
    }

    public void clickCreate(ActionEvent actionEvent) throws PMCException {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/CreateMovieView.fxml"));
            Parent newWindow = loader.load();
            Stage stage = new Stage();
            stage.setTitle("New Movie");
            stage.setScene(new Scene(newWindow));

            CreateMovieController controller = loader.getController();
            controller.setParentController(this);
            controller.setStage(stage);
            stage.showAndWait();
        } catch (IOException e) {
            throw new PMCException("Could not load new Stage", e);
        }
    }

    public void clickDelete(ActionEvent actionEvent) throws PMCException {
        try {
            movieModel.delete(movieModel.getCurrentMovie());
        } catch (Exception e) {
            throw new PMCException("Could not delete Movie",e);
        }
    }

    public void clickAddToCat(ActionEvent actionEvent) throws PMCException {
        try {
            //Gets the current movie and category, and retrieves their id's.
            CatMovie catMovie = new CatMovie(catModel.getCategory().getId(),
                    movieModel.getCurrentMovie().getId());
            catMovieModel.create(catMovie);
        } catch (Exception e) {
            throw new PMCException("Could not add to the specific Category",e);
        }
    }

    public void clickPlay(ActionEvent actionEvent) throws PMCException {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/PlayMovieView.fxml"));
            Parent newWindow = loader.load();
            Stage stage = new Stage();
            stage.setTitle("MediaPlayer");
            stage.setScene(new Scene(newWindow));

            MediaPlayerController controller = loader.getController();
            controller.setParentController(this);
            controller.setStage(stage);
            stage.showAndWait();
        } catch (IOException e) {
            throw new PMCException("Could not play movie", e);
        }
    }

    public void setMovie(MouseEvent mouseEvent) {
        movieModel.setMovie(tblMovies.getSelectionModel().getSelectedItem());
    }

    public void search()
    {
        txtMovieSearch.textProperty().addListener((observable, oldValue, newValue) ->
        {
            try
            {
                movieModel.searchMovie(newValue);
            }
            catch (PMCException e)
            {
                e.printStackTrace();
            }
        });
    }

}
