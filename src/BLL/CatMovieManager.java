package BLL;


import BE.CatMovie;
import BE.Movie;
import DAL.Dao.CatMovieDAO;
import DAL.ICatMovieDataAccess;
import utility.PMCException;

import java.util.List;


public class CatMovieManager {
    private ICatMovieDataAccess catMovieDAO;
    public CatMovieManager() throws PMCException {
        catMovieDAO = new CatMovieDAO();
    }

    public List<CatMovie> getAll() throws PMCException {
        return catMovieDAO.getAll();
    }

    public CatMovie create(CatMovie catMovie) throws PMCException {
        return catMovieDAO.create(catMovie);
    }

    public void update(CatMovie catMovie) throws PMCException {
        catMovieDAO.update(catMovie);
    }

    public void delete(CatMovie catMovie) throws PMCException {
        catMovieDAO.delete(catMovie);
    }

    //NOT IMPLEMENTED
    public CatMovie getById(int catMovieId) throws PMCException {
        return catMovieDAO.getById(catMovieId);
    }

    public int getMoviesCountForCategory(int categoryId) throws PMCException
    {
        return catMovieDAO.getMoviesCountForCategory(categoryId);
    }

    public List<Movie> getMovieByCatId(int catId) throws PMCException {
        return catMovieDAO.getMovieByCatId(catId);
    }
}
