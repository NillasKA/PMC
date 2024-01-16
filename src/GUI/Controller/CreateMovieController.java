package GUI.Controller;

import BE.CatMovie;
import BE.Category;
import BE.Movie;
import GUI.Model.APIModel;
import GUI.Model.CatModel;
import GUI.Model.CatMovieModel;
import GUI.Model.MovieModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import org.controlsfx.control.textfield.AutoCompletionBinding;
import org.controlsfx.control.textfield.TextFields;
import utility.PMCException;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
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
    private APIModel apiModel;
    private Stage stage;
    private int catId;
    private String filepath;

    public CreateMovieController() throws PMCException {
        try {
            movieModel = MovieModel.getInstance();
            catModel = CatModel.getInstance();
            catMovieModel = CatMovieModel.getInstance();
            apiModel = APIModel.getInstance();

        } catch (Exception e) {
            throw new PMCException("Could not fetch model instance", e);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            choiceBoxFirst.getItems().addAll(catModel.getObservableCategories());
            choiceBoxFirst.setOnAction(this::getSelectedCategory);
            autoComplete();
        } catch (PMCException e) {

        }
    }

    public void autoComplete() throws PMCException {
        txtName.textProperty().addListener((observable, oldValue, newValue) -> {
            try {
                String searchResult = apiModel.search(txtName.getText());
                List<Movie> movies = apiModel.getMovies(searchResult);

                AutoCompletionBinding<Movie> autoCompletionBinding = TextFields.bindAutoCompletion(txtName, movies);

                autoCompletionBinding.setOnAutoCompleted(
                        e -> {
                            movieModel.setMovie(e.getCompletion());
                            txtRating.setText(String.valueOf(e.getCompletion().getRating()));
                        });
            } catch (RuntimeException | PMCException e) {}});
    }

    public void clickCreate(ActionEvent actionEvent) throws PMCException {
        try {
            if (filepath != null){
                Movie movie = new Movie(-1, movieModel.getCurrentMovie().getTMDBId(),
                        txtName.getText(), Double.parseDouble(txtRating.getText()),
                        filepath, "never");

                movieModel.create(movie);
                System.out.println("last movie = " + movieModel.getAll().getLast().getId());
                categoryCreation(movieModel.getAll().getLast());

                stage.close();
            }
        } catch (Exception e) {
            throw new PMCException("Could not create movie", e);
        }
    }

    public void categoryCreation(Movie movie) throws PMCException, IOException, InterruptedException {
        List<Category> allCats = catModel.getAll();
        List<Category> cats = apiModel.getCategories(movie.getTMDBId());

        // Creates CatMovie, if we already have a category that this movie is a part of.
        for (Category category : allCats) {
            for (Category cat : cats) {
                if (cat.getName().equals(category.getName())) {
                    CatMovie catMovie = new CatMovie(category.getId(), movie.getId());
                    catMovieModel.create(catMovie);
                }
            }
        }
        //Creates a new Category and CatMovie if we dont already have the category this movie is a part of.
        for (Category cat : cats) {
            boolean found = false;
            for (Category category : allCats) {
                if (cat.getName().equals(category.getName())) {
                    found = true;
                }
            }
            if (!found) {
                Category newCat = new Category(-1, cat.getName());
                catModel.create(newCat);

                CatMovie catMovie = new CatMovie(catModel.getAll().getLast().getId(), movie.getId());
                catMovieModel.create(catMovie);
            }
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
