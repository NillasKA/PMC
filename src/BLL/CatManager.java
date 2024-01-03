package BLL;

import BE.Category;
import DAL.Dao.CatDAO;
import DAL.ICatDataAccess;

import java.util.List;

public class CatManager {
    private ICatDataAccess catDAO;
    public CatManager() throws Exception {
        catDAO = new CatDAO();
    }

    public List<Category> getAll() throws Exception {
        return catDAO.getAll();
    }

    public Category create(Category category) throws Exception {
        return catDAO.create(category);
    }

    public void update(Category category) throws Exception {
        catDAO.update(category);
    }

    public void delete(Category category) throws Exception {
        catDAO.delete(category);
    }

    public Category getById(int catId) throws Exception {
        return catDAO.getById(catId);
    }
}
