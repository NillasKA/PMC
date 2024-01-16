package GUI.Model;

import BE.Movie;
import BLL.APIManager;
import utility.PMCException;

import java.io.IOException;
import java.util.List;

public class APIModel {
    private static APIModel instance;
    private APIManager apiManager;

    public APIModel() throws PMCException {
        apiManager = new APIManager();
    }

    public static APIModel getInstance() throws PMCException {
        if(instance == null)
        {
            instance = new APIModel();
        }
        return instance;
    }

    public List<Movie> getMovies(String string){
        return apiManager.getMovies(string);
    }

    public String search(String string) throws PMCException {
        try {
            return apiManager.search(string);
        } catch (IOException e) {
            throw new PMCException(e);
        } catch (InterruptedException e) {
            throw new PMCException(e);
        }
    }
}
