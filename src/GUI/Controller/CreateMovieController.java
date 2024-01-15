package GUI.Controller;

import BE.CatMovie;
import BE.Category;
import BE.Movie;
import GUI.Model.CatModel;
import GUI.Model.CatMovieModel;
import GUI.Model.MovieModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import utility.PMCException;

import javax.swing.*;
import java.io.File;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ResourceBundle;

public class CreateMovieController implements Initializable {
    @FXML
    private Button btnFilePath;
    @FXML
    private TextField txtName, txtRating;
    @FXML
    private Label lblLastViewed;
    @FXML
    private ChoiceBox<Category> choiceBoxFirst;
    private BrowseViewController parentController;
    private MovieModel movieModel;
    private CatModel catModel;
    private CatMovieModel catMovieModel;
    private Stage stage;
    private int catId;
    private String filepath;

    public CreateMovieController() throws PMCException {
        try {
            movieModel = MovieModel.getInstance();
            catModel = CatModel.getInstance();
            catMovieModel = CatMovieModel.getInstance();
        } catch (Exception e) {
            throw new PMCException("Could not fetch model instance", e);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        choiceBoxFirst.getItems().addAll(catModel.getObservableCategories());
        choiceBoxFirst.setOnAction(this::getSelectedCategory);
    }

    public void clickCreate(ActionEvent actionEvent) throws PMCException {
        try {
            if (filepath != null){
                Movie movie = new Movie(-1, txtName.getText(), Double.parseDouble(txtRating.getText()),
                        filepath, "never");

                movieModel.create(movie);
                //Puts the movie into the selected category.
                if(choiceBoxFirst.getItems() != null) {
                    CatMovie catMovie = new CatMovie(catId, movieModel.getAll().getLast().getId()); //This because otherwise -1 would be movie ID. Wouldnt work.
                    catMovieModel.create(catMovie);
                }
                stage.close();
            }
        } catch (Exception e) {
            throw new PMCException("Could not create movie", e);
        }
    }

    public void clickMovieFile(ActionEvent actionEvent) throws PMCException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose a File");

        // Set the initial directory, if needed
        //Idk why i cant just do user.dir/data - But io.file has its limitations i guess.
        fileChooser.setInitialDirectory(new File(System.getProperty("user.dir") + File.separator + "data"));
        File selectedFile = fileChooser.showOpenDialog(new Stage());

        if(selectedFile.getName().contains(".mp4") || selectedFile.getName().contains(".mpeg4") && selectedFile != null){
            // Set the selected file path to the button text.
            btnFilePath.setText(getRelativePath(selectedFile.getPath()));
            filepath = getRelativePath(selectedFile.getPath());
        }
        else {
            btnFilePath.setText("WRONG FILE FORMAT");
            warning();
        }
    }

    private String getRelativePath(String fullPath) throws PMCException {
        Path fullPathObj = Paths.get(fullPath);
        Path baseDir = Paths.get(System.getProperty("user.dir"));

        try
        {
            Path relativePath = baseDir.relativize(fullPathObj);
            return relativePath.toString();
        } catch (IllegalArgumentException e)
        {
            throw new PMCException(e);
        }
    }

    public void warning(){
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Warning");
        alert.setContentText("Could not create a movie, check the file format. Only .mp4 and .mpeg4 is allowed.");
        alert.showAndWait();
    }

    public void getSelectedCategory(ActionEvent event) {
        Category cat = choiceBoxFirst.getValue();
        catId = cat.getId();
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
