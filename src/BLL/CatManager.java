package BLL;

import BE.Category;
import DAL.Dao.CatDAO;
import DAL.ICatDataAccess;
import utility.PMCException;

import java.util.List;

public class CatManager {
    private ICatDataAccess catDAO;
    public CatManager() throws PMCException {
        catDAO = new CatDAO();
    }

    public List<Category> getAll() throws PMCException {
        return catDAO.getAll();
    }

    public Category create(Category category) throws PMCException {
        return catDAO.create(category);
    }

    public void update(Category category) throws PMCException {
        catDAO.update(category);
    }

    public void delete(Category category) throws PMCException {
        catDAO.delete(category);
    }

    public Category getById(int catId) throws PMCException {
        return catDAO.getById(catId);
    }
}
