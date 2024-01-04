package GUI.Controller;

import GUI.Model.CatModel;
import GUI.Model.CatMovieModel;
import GUI.Model.MovieModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
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
    }
}
