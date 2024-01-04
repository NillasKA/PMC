package GUI.Controller;

import BE.Movie;
import GUI.Model.MovieModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class CreateMovieController {
    @FXML
    private Button btnFilePath;
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
            String fullpath = btnFilePath.getText();
            String relativePath = getRelativePath(fullpath);

            Movie movie = new Movie(-1, txtName.getText(), Double.parseDouble(txtRating.getText()),
                    relativePath, "never");

            movieModel.create(movie);
            stage.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void clickMovieFile(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose a File");

        // Set the initial directory, if needed
        //Idk why i cant just do user.dir/data - But io.file has its limitations i guess.
        fileChooser.setInitialDirectory(new File(System.getProperty("user.dir") + File.separator + "data"));
        File selectedFile = fileChooser.showOpenDialog(new Stage());

        if (selectedFile != null) {
            // Set the selected file path to the button text.
            btnFilePath.setText(getRelativePath(selectedFile.getPath()));
        }
    }

    private String getRelativePath(String fullPath) {
        int indexOfData = fullPath.indexOf("data");
        if(indexOfData != -1)
        {
            return fullPath.substring(indexOfData);
        }
        else
        {
            return fullPath;
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
}
