package GUI.Controller;

import BE.Category;
import GUI.Model.CatModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import utility.PMCException;

public class CreateCategoryController {
    @FXML
    private TextField txtName;
    @FXML
    private Stage stage;
    private MainViewController parentController;

    private CatModel catModel;

    public CreateCategoryController() throws PMCException {
        try {
            catModel = CatModel.getInstance();
        } catch (Exception e) {
            throw new PMCException("Could not fetch model instance", e);
        }
    }

    public void clickCreate(ActionEvent actionEvent) throws PMCException {
        try {
            String name = txtName.getText();
            Category cat = new Category(-1, name);
            catModel.create(cat);
            stage.close();
        } catch (Exception e) {
            throw new PMCException("Could not create Category", e);
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
