package GUI.Controller;

import BE.Category;
import GUI.Model.CatModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class CreateCategoryController {
    @FXML
    private TextField txtName;
    @FXML
    private Stage stage;
    private MainViewController parentController;

    CatModel catModel;

    public CreateCategoryController(){
        try {
            catModel = CatModel.getInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void clickCreate(ActionEvent actionEvent) {
        try {
            String name = txtName.getText();
            Category cat = new Category(-1, name);
            catModel.create(cat);
            stage.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void clickCancel(ActionEvent actionEvent) {
        stage.close();
    }

    public void setParentController(MainViewController parentController) {
        this.parentController = parentController;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }
}
