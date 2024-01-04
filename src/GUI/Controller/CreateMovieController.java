package GUI.Controller;

import BE.Movie;
import GUI.Model.MovieModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class CreateMovieController {
    @FXML
    private TextField txtName, txtRating;
    @FXML
    private Label lblLastViewed;
    BrowseViewController parentController;
    MovieModel movieModel;
    private Stage stage;

    public CreateMovieController(){
        try {
            movieModel = MovieModel.getInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void clickCreate(ActionEvent actionEvent) {
        try {
            //"Filler" and "never" just temporary replacements.
            Movie movie = new Movie(-1, txtName.getText(), Double.parseDouble(txtRating.getText()),
                    "filler", "never");

            movieModel.create(movie);
            stage.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public void clickCancel(ActionEvent actionEvent) {
        stage.close();
    }

    public void setParentController(BrowseViewController parentController) {
        this.parentController = parentController;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void clickMovieFile(ActionEvent actionEvent) {
    }
}
