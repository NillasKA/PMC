package GUI.Model;

import BE.Category;
import BLL.CatManager;

import java.util.List;

public class CatModel {
    private static CatModel instance;
    private CatManager catManager;

    public CatModel() throws Exception {
        catManager = new CatManager();
    }

    public static CatModel getInstance() throws Exception {
        if(instance == null)
        {
            instance = new CatModel();
        }
        return instance;
    }

    public List<Category> getAll() throws Exception {
        return catManager.getAll();
    }

    public Category create(Category category) throws Exception {
        return catManager.create(category);
    }

    public void update(Category category) throws Exception {
        catManager.update(category);
    }

    public void delete(Category category) throws Exception {
        catManager.delete(category);
    }

    public Category getById(int catId) throws Exception {
        return catManager.getById(catId);
    }
}
