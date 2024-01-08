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

    public MainViewController(){
        try {
            catModel = CatModel.getInstance();
            movieModel = MovieModel.getInstance();
            catMovieModel = CatMovieModel.getInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void clickBrowse(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/BrowseView.fxml"));
            Parent p = loader.load();
            BrowseViewController browseController = loader.getController();
            AnchorPane view = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/BrowseView.fxml")));
            borderPane.setCenter(view);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void clickCreate(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/CreateCategoryView.fxml"));
            Parent newWindow = loader.load();
            Stage stage = new Stage();
            stage.setTitle("New Category");
            stage.setScene(new Scene(newWindow));

            CreateCategoryController controller = loader.getController();
            controller.setParentController(this);
            controller.setStage(stage);
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public void clickDelete(ActionEvent actionEvent) {
        //Selection model on movie selected in tableview.
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

    public void setCategory(MouseEvent mouseEvent) {
        catModel.setCategory(tblCategories.getSelectionModel().getSelectedItem());
    }
}
