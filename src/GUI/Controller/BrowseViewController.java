package GUI.Controller;

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
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;

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
    private TableColumn<Movie, String> colRating = new TableColumn<>();

    @FXML
    private TableColumn<Movie, String> colName = new TableColumn<>();

    @FXML
    private TableColumn<Movie, String> colLastViewed = new TableColumn<>();

    @FXML
    private TableColumn<Movie, String> colFileLink = new TableColumn<>();


    public BrowseViewController() {
        try {
            movieModel = MovieModel.getInstance();
            catMovieModel = CatMovieModel.getInstance();
            catModel = CatModel.getInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void clickCreate(ActionEvent actionEvent) {
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
            throw new RuntimeException(e);
        }
    }

    public void clickDelete(ActionEvent actionEvent) {
        //Selection model on movie selected in tableview.
    }

    public void clickPlay(ActionEvent actionEvent) {
        //Selection model on movie selected in tableview.
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tblMovies.setItems(movieModel.getObservableMovies());
        colRating.setCellValueFactory(new PropertyValueFactory<>("rating"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        //colLastViewed.setCellValueFactory(new PropertyValueFactory<>("lastviewed"));
        colFileLink.setCellValueFactory(new PropertyValueFactory<>("filelink"));

        tblMovies.setEditable(true);


    }
}
