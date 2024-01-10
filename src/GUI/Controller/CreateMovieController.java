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
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
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

    public CreateMovieController(){
        try {
            movieModel = MovieModel.getInstance();
            catModel = CatModel.getInstance();
            catMovieModel = CatMovieModel.getInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        choiceBoxFirst.getItems().addAll(catModel.getObservableCategories());
        choiceBoxFirst.setOnAction(this::getSelectedCategory);
    }

    public void clickCreate(ActionEvent actionEvent) {
        try {
            String fullpath = btnFilePath.getText();
            String relativePath = getRelativePath(fullpath);

            Movie movie = new Movie(-1, txtName.getText(), Double.parseDouble(txtRating.getText()),
                    relativePath, "never");

            movieModel.create(movie);
            //Puts the movie into the selected category.
            if(choiceBoxFirst.getItems() != null) {
                CatMovie catMovie = new CatMovie(catId, movieModel.getAll().getLast().getId()); //This because otherwise -1 would be movie ID. Wouldnt work.
                catMovieModel.create(catMovie);
            }

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
        if(indexOfData != -1) {
            return fullPath.substring(indexOfData);
        }
        else {
            return fullPath;
        }
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
