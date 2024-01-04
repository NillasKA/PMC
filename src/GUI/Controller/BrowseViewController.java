package GUI.Controller;

import GUI.Model.MovieModel;

public class BrowseViewController {
    MovieModel movieModel;

    public BrowseViewController(){
        try {
            movieModel = MovieModel.getInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
