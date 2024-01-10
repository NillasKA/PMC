package GUI.Model;

import BE.Category;
import BLL.CatManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import utility.PMCException;

import java.util.List;

public class CatModel {
    private static CatModel instance;
    private CatManager catManager;

    private ObservableList<Category> allCategories;
    private Category currentCat;

    public CatModel() throws PMCException {
        catManager = new CatManager();
        allCategories = FXCollections.observableArrayList();
        allCategories.addAll(catManager.getAll());
    }

    public static CatModel getInstance() throws PMCException {
        if(instance == null)
        {
            instance = new CatModel();
        }
        return instance;
    }

    public ObservableList<Category> getObservableCategories()
    {
        return allCategories;
    }

    public List<Category> getAll() throws PMCException {
        return catManager.getAll();
    }

    public Category create(Category category) throws PMCException {
        allCategories.add(category);
        return catManager.create(category);
    }

    public void update(Category category) throws PMCException {
        catManager.update(category);
    }

    public void delete(Category category) throws PMCException {
        catManager.delete(category);
        allCategories.remove(category);
    }

    public Category getById(int catId) throws PMCException {
        return catManager.getById(catId);
    }

    public void setCategory(Category currentCat){
        this.currentCat = currentCat;
    }

    public Category getCategory(){
        return currentCat;
    }
}
