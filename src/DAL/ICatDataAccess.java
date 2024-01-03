package DAL;

import BE.Category;
import BE.Movie;

import java.util.List;

public interface ICatDataAccess {
    public List<Category> getAll() throws Exception;

    public Category create(Category category) throws Exception;

    public void update(Category category) throws Exception;

    public void delete(Category category) throws Exception;

    public Category getById(int catId) throws Exception;
}
