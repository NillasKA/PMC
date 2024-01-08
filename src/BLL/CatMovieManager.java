package BLL;


import BE.CatMovie;
import BE.Movie;
import DAL.Dao.CatMovieDAO;
import DAL.ICatMovieDataAccess;

import java.util.List;


public class CatMovieManager {
    private ICatMovieDataAccess catMovieDAO;
    public CatMovieManager() throws Exception {
        catMovieDAO = new CatMovieDAO();
    }

    public List<CatMovie> getAll() throws Exception {
        return catMovieDAO.getAll();
    }

    public CatMovie create(CatMovie catMovie) throws Exception {
        return catMovieDAO.create(catMovie);
    }

    public void update(CatMovie catMovie) throws Exception {
        catMovieDAO.update(catMovie);
    }

    public void delete(CatMovie catMovie) throws Exception {
        catMovieDAO.delete(catMovie);
    }

    //NOT IMPLEMENTED
    public CatMovie getById(int catMovieId) throws Exception {
        return catMovieDAO.getById(catMovieId);
    }

    public int getMoviesCountForCategory(int categoryId) throws Exception
    {
        return catMovieDAO.getMoviesCountForCategory(categoryId);
    }

    public List<Movie> getMovieByCatId(int catId) throws Exception {
        return catMovieDAO.getMovieByCatId(catId);
    }
}
