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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import utility.PMCException;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class CategoryViewController implements Initializable {
    private MovieModel movieModel;
    private CatMovieModel catMovieModel;
    private CatModel catModel;
    @FXML
    private TableView<Movie> tblMovies;
    @FXML
    private TableColumn<Movie, String> colRating = new TableColumn<>();
    @FXML
    private TableColumn<Movie, String> colName = new TableColumn<>();
    @FXML
    private TableColumn<Movie, String> colLastViewed = new TableColumn<>();
    @FXML
    private TableColumn<Movie, String> colFileLink = new TableColumn<>();

    public CategoryViewController() throws PMCException {
        try {
            movieModel = MovieModel.getInstance();
            catMovieModel = CatMovieModel.getInstance();
            catModel = CatModel.getInstance();


        } catch (Exception e) {
            throw new PMCException("Could not fetch model instances",e);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources){
        try {
            tblMovies.setItems(catMovieModel.getObservableCatMovies());
            colRating.setCellValueFactory(new PropertyValueFactory<>("rating"));
            colName.setCellValueFactory(new PropertyValueFactory<>("name"));
            //colLastViewed.setCellValueFactory(new PropertyValueFactory<>("lastviewed"));
            colFileLink.setCellValueFactory(new PropertyValueFactory<>("filelink"));
            tblMovies.setEditable(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setMovie(MouseEvent mouseEvent) {
        movieModel.setMovie(tblMovies.getSelectionModel().getSelectedItem());
    }

    public void clickPlay(ActionEvent actionEvent) throws PMCException {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/PlayMovie.fxml"));
            Parent newWindow = loader.load();
            Stage stage = new Stage();
            stage.setTitle("MediaPlayer");
            stage.setScene(new Scene(newWindow));

            MediaPlayerController controller = loader.getController();
            controller.setStage(stage);
            stage.showAndWait();
        } catch (IOException e) {
            throw new PMCException("Could not play movie", e);
        }
    }


    public void clickDelete(ActionEvent actionEvent) throws PMCException {
        try {
            Movie movie = tblMovies.getSelectionModel().getSelectedItem();
            CatMovie catMovie = new CatMovie(catModel.getCategory().getId(), movie.getId());
            catMovieModel.delete(catMovie);
        } catch (Exception e) {
            throw new PMCException(e);
        }
    }
}
