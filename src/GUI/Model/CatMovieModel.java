package GUI.Model;

import BE.CatMovie;
import BLL.CatMovieManager;

import java.util.List;

public class CatMovieModel {
    private static CatMovieModel instance;
    private CatMovieManager catMovieManager;

    public CatMovieModel() throws Exception {
        catMovieManager = new CatMovieManager();
    }

    public static CatMovieModel getInstance() throws Exception {
        if(instance == null)
        {
            instance = new CatMovieModel();
        }
        return instance;
    }

    public List<CatMovie> getAll() throws Exception {
        return catMovieManager.getAll();
    }

    public CatMovie create(CatMovie catMovie) throws Exception {
        return catMovieManager.create(catMovie);
    }

    public void update(CatMovie catMovie) throws Exception {
        catMovieManager.update(catMovie);
    }

    public void delete(CatMovie catMovie) throws Exception {
        catMovieManager.delete(catMovie);
    }

    //NOT IMPLEMENTED
    public CatMovie getById(int catMovieId) throws Exception {
        return catMovieManager.getById(catMovieId);
    }
}
