package GUI.Controller;

import GUI.Model.CatModel;
import GUI.Model.CatMovieModel;
import GUI.Model.MovieModel;
import BE.Category;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
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
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.util.Callback;
import utility.PMCException;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class MainViewController implements Initializable {
    private CatModel catModel;
    private MovieModel movieModel;
    private CatMovieModel catMovieModel;

    @FXML
    private BorderPane borderPane;

    @FXML
    private TableView<Category> tblCategories;
    @FXML
    private TableColumn<Category, String> colCategories;
    @FXML
    private TableColumn<Category, String> colMovies;
    private Stage stage;

    public MainViewController() throws PMCException {
        try {
            catModel = CatModel.getInstance();
            movieModel = MovieModel.getInstance();
            catMovieModel = CatMovieModel.getInstance();
        } catch (Exception e) {
            throw new PMCException("Could not fetch model instances", e);
        }
    }

    public void clickBrowse(ActionEvent actionEvent) throws PMCException {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/BrowseView.fxml"));
            Parent p = loader.load();
            BrowseViewController browseController = loader.getController();
            AnchorPane view = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/BrowseView.fxml")));
            borderPane.setCenter(view);
        } catch (IOException e) {
            throw new PMCException("Could not load browsing window", e);
        }
    }

    public void clickCategory(MouseEvent mouseEvent) throws PMCException {
        try {
            //Sets the selected category to the model.
            catModel.setCategory(tblCategories.getSelectionModel().getSelectedItem());
            catMovieModel.setCategory(tblCategories.getSelectionModel().getSelectedItem());
            catMovieModel.initCurrentCat();

            //Loads the movies in the category.
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/CategoryView.fxml"));
            Parent p = loader.load();
            CategoryViewController categoryViewController = loader.getController();
            AnchorPane view = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/CategoryView.fxml")));
            borderPane.setCenter(view);
        } catch (Exception e) {
            throw new PMCException("Could not load movies in category",e);
        }
    }

    public void clickCreate(ActionEvent actionEvent) throws PMCException {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/CreateCategoryView.fxml"));
            Parent newWindow = loader.load();
            Stage stage = new Stage();
            stage.setTitle("New Category");
            stage.setScene(new Scene(newWindow));

            CreateCategoryController controller = loader.getController();
            controller.setParentController(this);
            controller.setStage(stage);
            tblCategories.refresh();
            stage.showAndWait();
            tblCategories.refresh();
        } catch (IOException e) {
            e.printStackTrace();
            throw new PMCException("Could not load creator window",e);
        }
    }

    public void clickDelete(ActionEvent actionEvent) throws PMCException {
        try {
            Category cat = tblCategories.getSelectionModel().getSelectedItem();
            catModel.delete(cat);
            tblCategories.refresh();
        } catch (Exception e) {
            throw new PMCException("Could not delete category",e);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tblCategories.setItems(catModel.getObservableCategories());
        colCategories.setCellValueFactory(new PropertyValueFactory<>("name"));

        colMovies.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Category, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Category, String> param) {
                Category category = param.getValue();
                int movieCount = 0;

                try {
                    movieCount = catMovieModel.getMoviesCountForCategory(category.getId());
                } catch (Exception e) {
                    e.printStackTrace();
                }

                return new SimpleStringProperty(String.valueOf(movieCount));
            }
        });

        tblCategories.setEditable(true);
    }

    public void setParentController(WarningController warningController) {
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void closeStage() {
        if (stage !=null){
            stage.close();
        }
        
    }
}
