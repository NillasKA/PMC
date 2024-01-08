package DAL;

import BE.CatMovie;
import BE.Category;
import BE.Movie;

import java.util.List;

public interface ICatMovieDataAccess {
    public List<CatMovie> getAll() throws Exception;

    public CatMovie create(CatMovie catMovie) throws Exception;

    public void update(CatMovie catMovie) throws Exception;

    public void delete(CatMovie catMovie) throws Exception;

    public CatMovie getById(int catMovieId) throws Exception;

    public int getMoviesCountForCategory(int categoryId) throws Exception;

    List<Movie> getMovieByCatId(int catId) throws Exception;
}
