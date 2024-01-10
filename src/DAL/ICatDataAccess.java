package DAL;

import BE.Category;
import BE.Movie;
import utility.PMCException;

import java.util.List;

public interface ICatDataAccess {
    public List<Category> getAll() throws PMCException;

    public Category create(Category category) throws PMCException;

    public void update(Category category) throws PMCException;

    public void delete(Category category) throws PMCException;

    public Category getById(int catId) throws PMCException;
}
