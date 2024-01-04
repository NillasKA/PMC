package GUI.Controller;

import GUI.Model.CatModel;
import javafx.event.ActionEvent;

public class MainViewController {
    CatModel catModel;

    public MainViewController(){
        try {
            catModel = CatModel.getInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void clickBrowse(ActionEvent actionEvent) {
    }

    public void clickCreate(ActionEvent actionEvent) {
    }

    public void clickDelete(ActionEvent actionEvent) {
    }
}
