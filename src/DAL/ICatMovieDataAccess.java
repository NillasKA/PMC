package DAL;

import BE.CatMovie;
import BE.Category;
import BE.Movie;
import utility.PMCException;

import java.util.List;

public interface ICatMovieDataAccess {
    public List<CatMovie> getAll() throws PMCException;

    public CatMovie create(CatMovie catMovie) throws PMCException;

    public void update(CatMovie catMovie) throws PMCException;

    public void delete(CatMovie catMovie) throws PMCException;

    public CatMovie getById(int catMovieId) throws PMCException;

    public int getMoviesCountForCategory(int categoryId) throws PMCException;

    List<Movie> getMovieByCatId(int catId) throws PMCException;
}
